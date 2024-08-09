package com.example.filterdemo.service;

import com.example.filterdemo.dto.RequestDto;
import com.example.filterdemo.dto.SearchRequestDto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FilterSpecification<T> {

    public Specification<T> getSearchSpecification(SearchRequestDto searchRequestDto) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get(searchRequestDto.getColumn()), searchRequestDto.getValue());
            }
        };

    }

    public Specification<T> getSearchSpecification(List<SearchRequestDto> searchRequestDtoList, RequestDto.GlobalOperator globalOperator) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicateList = new ArrayList<>();
            for(SearchRequestDto searchRequestDto : searchRequestDtoList) {
                switch (searchRequestDto.getOperation()){
                    case EQUAL:
                        Predicate equal= criteriaBuilder.equal(root.get(searchRequestDto.getColumn()), searchRequestDto.getValue());
                        predicateList.add(equal);
                        break;
                    case LIKE:
                        Predicate like = criteriaBuilder.like(root.get(searchRequestDto.getColumn()), "%"+ searchRequestDto.getValue()+"%");
                        predicateList.add(like);
                        break;
                    case IN:
                        String[] split = searchRequestDto.getValue().split(",");
                        Predicate in =root.get(searchRequestDto.getColumn()).in(Arrays.asList(split));
                        predicateList.add(in);
                        break;
                    case LESS_THAN:
                        Predicate less = criteriaBuilder.lessThan(root.get(searchRequestDto.getColumn()), searchRequestDto.getValue());
                        predicateList.add(less);
                        break;
                    case GREATER_THAN:
                        Predicate greater = criteriaBuilder.greaterThan(root.get(searchRequestDto.getColumn()), searchRequestDto.getValue());
                        predicateList.add(greater);
                        break;
                    case BETWEEN:
                        String[] spli = searchRequestDto.getValue().split(",");
                        Predicate between = criteriaBuilder.between(root.get(searchRequestDto.getColumn()),Long.parseLong(spli[0]),Long.parseLong(spli[1]));
                        predicateList.add(between);
                        break;
                    case JOIN:
                        Predicate join = criteriaBuilder.equal(root.join(searchRequestDto.getJoinTable()).get(searchRequestDto.getColumn()),searchRequestDto.getValue());
                        predicateList.add(join);
                        break;
                    default:
                        throw new UnsupportedOperationException("Operation not supported");
                }



            }
            if(globalOperator.equals(RequestDto.GlobalOperator.AND)) {
                return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
            }
            else{
                return criteriaBuilder.or(predicateList.toArray(new Predicate[0]));
            }
            };
    }

    public Specification<T> getSearchName(String name) {
        return (root, query, criteriaBuilder) -> {

            Predicate Like = criteriaBuilder.like(root.get("name"), "%"+name+"%");
            System.out.println(Like);
            return criteriaBuilder.and(Like);
        };
    }

    public Specification<T> getSearchBetween(String name) {
        return (root, query, criteriaBuilder) -> {
            String[] split = name.split(",");
            Predicate Like = criteriaBuilder.between(root.get("id"),split[0],split[1]);
            return criteriaBuilder.and(Like);
        };
    }
}
