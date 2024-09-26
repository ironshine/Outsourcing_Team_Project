package com.sparta.outsourcing_team_project.domain.search.service;

import com.sparta.outsourcing_team_project.domain.store.dto.StoresResponseDto;
import com.sparta.outsourcing_team_project.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

    private final StoreRepository storeRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    public List<StoresResponseDto> searchStoreList(String searchTerm) {

        TimeUnit timeUnit = TimeUnit.HOURS;

        if (redisTemplate.opsForValue().get("search:" + searchTerm) == null) {
            redisTemplate.opsForValue().set("search:" + searchTerm, "1");
        } else {
            redisTemplate.opsForValue().increment("search:" + searchTerm);
        }
        redisTemplate.expire("search:" + searchTerm, 1, timeUnit);

        return storeRepository.findByStoreNameOrMenuNameOrderByAdPrice(searchTerm).stream()
                .map(StoresResponseDto::new)
                .toList();
    }

    public List<String> searchHotSearchTerm() {

        Map<String, Integer> resultMap = new LinkedHashMap<>();

        ScanOptions options = ScanOptions.scanOptions().match("search" + "*").count(100).build();
        Cursor<byte[]> cursor = redisTemplate.getConnectionFactory().getConnection().scan(options);

        while (cursor.hasNext()) {
            String key = new String(cursor.next());
            String value = String.valueOf(redisTemplate.opsForValue().get(key));
            resultMap.put(key, Integer.valueOf(value));
        }

        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(resultMap.entrySet());

        // 내림차순으로 정렬
        entryList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // 인덱스 10 이후로 자르기
        if (entryList.size() > 10) {
            entryList = entryList.subList(0, 10);
        }

        // 정렬된 결과를 LinkedHashMap에 넣어 순서를 유지
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap.keySet().stream().map(s -> s.substring(7)).toList();
    }
}
