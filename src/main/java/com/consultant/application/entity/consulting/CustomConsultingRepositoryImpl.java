package com.consultant.application.entity.consulting;

import com.consultant.application.dto.response.ConsultingPages;
import com.consultant.application.entity.staff.QStaff;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Repository
public class CustomConsultingRepositoryImpl implements CustomConsultingRepository{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<?> findConsultingList(String consultantId, String managerId, Boolean isReading, Boolean isFeedback, Boolean consultingDateAsc, Pageable pageable) {
        QConsulting consulting = QConsulting.consulting;

        JPAQuery<Consulting> consultingJPAQuery = jpaQueryFactory.select(consulting)
                .from(consulting)
                .where(eqConsultantId(consultantId), eqManagerId(managerId), eqIsRead(isReading), eqIsFeedback(isFeedback))
                .orderBy(orderByAsc(consultingDateAsc)).offset(pageable.getOffset()).limit(pageable.getPageSize());

        Long elementsNum = countElements(consultantId, managerId, isReading, isFeedback);

        return new ConsultingPages(consultingJPAQuery.fetch(), pageable, elementsNum);
    }

    private Long countElements(String consultantId, String managerId, Boolean isReading, Boolean isFeedback) {
        QConsulting consulting = QConsulting.consulting;

        JPAQuery<Long> countTotalQuery = jpaQueryFactory.select(consulting.count())
                .from(consulting)
                .where(eqConsultantId(consultantId), eqManagerId(managerId), eqIsRead(isReading), eqIsFeedback(isFeedback));

        return countTotalQuery.fetchOne();

    }

    private BooleanExpression eqConsultantId(String consultantId) {
        if(consultantId == null)
            return null;

        QStaff consultant = QConsulting.consulting.consultant;
        return consultant.id.eq(consultantId);

    }

    private BooleanExpression eqManagerId(String managerId) {
        if(managerId == null)
            return null;

        QStaff manager = QConsulting.consulting.manager;
        return manager.id.eq(managerId);
    }

    private BooleanExpression eqIsRead(Boolean isReading) {

        if(isReading == null) {
            return null;
        }

        BooleanPath qIsReading = QConsulting.consulting.isReading;
        return qIsReading.eq(isReading);
    }

    private BooleanExpression eqIsFeedback(Boolean isFeedback) {
        if(isFeedback == null) {
            return null;
        }
        StringPath qFeedback = QConsulting.consulting.feedback;
        if(isFeedback) {
            return qFeedback.isNotNull();
        }
        return qFeedback.isNull();
    }

    private OrderSpecifier<LocalDateTime> orderByAsc(Boolean consultingDateAsc) {
        QConsulting consulting = QConsulting.consulting;
        if(consultingDateAsc == null || !consultingDateAsc) {
            return consulting.createdAt.desc();
        }
        return consulting.createdAt.asc();
    }

}
