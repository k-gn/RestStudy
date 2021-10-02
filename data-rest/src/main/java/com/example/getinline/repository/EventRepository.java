package com.example.getinline.repository;

import com.example.getinline.constant.EventStatus;
import com.example.getinline.domain.Event;
import com.example.getinline.domain.QEvent;
import com.example.getinline.dto.EventDTO;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
// Spring Data Rest
// Restful 원칙을 지키고 간단한 기능의 API를 만드는데는 좋다.
// Entity와 Repository만 만들어주면 API가 자동 생성된다. (페이징과 link 도 전부 자동으로 됨)
// 검색을 사용하기 위해 querydsl 을 같이 사용하면 좋음

// 사실 Spring Data REST dependency가 추가되면 @RepositoryRestResource가 없어도 기본 설정으로 REST API가 만들어진다.
// 그럼 기본 설정이 아닌 추가 설정을 해야 한다면 @RepositoryRestResource가 필요하다.
//@RepositoryRestResource
public interface EventRepository extends
        JpaRepository<Event, Long>,
//        PagingAndSortingRepository<Event, Long>,
        QuerydslPredicateExecutor<Event>, // querydsl 로 자유로운 검색을 가능하게 해줌
        QuerydslBinderCustomizer<QEvent> { // 검색하고싶지 않은 내용이 있을 수 있어서 커스텀 가능하게 해줌

    @Override
    default void customize(QuerydslBindings bindings, QEvent root) {
        // 명시적 바인딩이 정의되지 않았거나 명시적으로 허용된 모든 속성을 제외할지 여부
        bindings.excludeUnlistedProperties(true);
        // 검색하고 싶은 대상 바인딩 설정
        bindings.including(root.placeId, root.eventName, root.eventStatus, root.eventStartDatetime, root.eventEndDatetime);
        // 각각 검색방식 설정
//        bindings.bind(root.eventName).first(((path, value) -> path.like(value)));
        bindings.bind(root.eventName).first(StringExpression::containsIgnoreCase);  // like
        bindings.bind(root.eventStartDatetime).first(ComparableExpression::goe); // ~ 이상
        bindings.bind(root.eventEndDatetime).first(ComparableExpression::loe); // ~ 이하
    }

    // search
    @RestResource(path = "name")
    List<Event> findAllByEventName(String name, Pageable pageable);


}
