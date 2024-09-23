package com.sparta.outsourcing_team_project.search.service;

import com.sparta.outsourcing_team_project.store.dto.StoresResponseDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.query.SortQueryBuilder;
import org.springframework.data.redis.core.query.SortQuery;
import com.sparta.outsourcing_team_project.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

//        if (redisTemplate.opsForZSet().score("search", searchTerm) == null) {
//            redisTemplate.opsForZSet().add("search", searchTerm, 1);
//        } else {
//            redisTemplate.opsForZSet().incrementScore("search", searchTerm, 1);
//        }

        if (redisTemplate.opsForValue().get("search:" + searchTerm) == null) {
            redisTemplate.opsForValue().set("search:" + searchTerm, "1");
        }
        redisTemplate.opsForValue().increment("search:" + searchTerm);
        redisTemplate.expire("search:" + searchTerm, 1, timeUnit);

        return storeRepository.findByStoreNameOrMenuName(searchTerm).stream()
                .map(StoresResponseDto::new)
                .toList();
    }

    public List<String> searchHotSearchTerm() {
//        return redisTemplate.opsForZSet().reverseRange("search", 0, 9).stream().
//                map(String::valueOf).toList();
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

        // 정렬된 결과를 LinkedHashMap에 넣어 순서를 유지
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap.keySet().stream().map(s -> s.substring(7)).toList();
    }
}
