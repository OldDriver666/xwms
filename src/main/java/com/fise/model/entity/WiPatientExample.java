package com.fise.model.entity;

import java.util.ArrayList;
import java.util.List;

public class WiPatientExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer limit;

    private Integer offset;

    public WiPatientExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getOffset() {
        return offset;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andDomainIsNull() {
            addCriterion("domain is null");
            return (Criteria) this;
        }

        public Criteria andDomainIsNotNull() {
            addCriterion("domain is not null");
            return (Criteria) this;
        }

        public Criteria andDomainEqualTo(String value) {
            addCriterion("domain =", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotEqualTo(String value) {
            addCriterion("domain <>", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainGreaterThan(String value) {
            addCriterion("domain >", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainGreaterThanOrEqualTo(String value) {
            addCriterion("domain >=", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainLessThan(String value) {
            addCriterion("domain <", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainLessThanOrEqualTo(String value) {
            addCriterion("domain <=", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainLike(String value) {
            addCriterion("domain like", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotLike(String value) {
            addCriterion("domain not like", value, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainIn(List<String> values) {
            addCriterion("domain in", values, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotIn(List<String> values) {
            addCriterion("domain not in", values, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainBetween(String value1, String value2) {
            addCriterion("domain between", value1, value2, "domain");
            return (Criteria) this;
        }

        public Criteria andDomainNotBetween(String value1, String value2) {
            addCriterion("domain not between", value1, value2, "domain");
            return (Criteria) this;
        }

        public Criteria andBodyStatusIsNull() {
            addCriterion("body_status is null");
            return (Criteria) this;
        }

        public Criteria andBodyStatusIsNotNull() {
            addCriterion("body_status is not null");
            return (Criteria) this;
        }

        public Criteria andBodyStatusEqualTo(String value) {
            addCriterion("body_status =", value, "bodyStatus");
            return (Criteria) this;
        }

        public Criteria andBodyStatusNotEqualTo(String value) {
            addCriterion("body_status <>", value, "bodyStatus");
            return (Criteria) this;
        }

        public Criteria andBodyStatusGreaterThan(String value) {
            addCriterion("body_status >", value, "bodyStatus");
            return (Criteria) this;
        }

        public Criteria andBodyStatusGreaterThanOrEqualTo(String value) {
            addCriterion("body_status >=", value, "bodyStatus");
            return (Criteria) this;
        }

        public Criteria andBodyStatusLessThan(String value) {
            addCriterion("body_status <", value, "bodyStatus");
            return (Criteria) this;
        }

        public Criteria andBodyStatusLessThanOrEqualTo(String value) {
            addCriterion("body_status <=", value, "bodyStatus");
            return (Criteria) this;
        }

        public Criteria andBodyStatusLike(String value) {
            addCriterion("body_status like", value, "bodyStatus");
            return (Criteria) this;
        }

        public Criteria andBodyStatusNotLike(String value) {
            addCriterion("body_status not like", value, "bodyStatus");
            return (Criteria) this;
        }

        public Criteria andBodyStatusIn(List<String> values) {
            addCriterion("body_status in", values, "bodyStatus");
            return (Criteria) this;
        }

        public Criteria andBodyStatusNotIn(List<String> values) {
            addCriterion("body_status not in", values, "bodyStatus");
            return (Criteria) this;
        }

        public Criteria andBodyStatusBetween(String value1, String value2) {
            addCriterion("body_status between", value1, value2, "bodyStatus");
            return (Criteria) this;
        }

        public Criteria andBodyStatusNotBetween(String value1, String value2) {
            addCriterion("body_status not between", value1, value2, "bodyStatus");
            return (Criteria) this;
        }

        public Criteria andPoliceInfoIsNull() {
            addCriterion("police_info is null");
            return (Criteria) this;
        }

        public Criteria andPoliceInfoIsNotNull() {
            addCriterion("police_info is not null");
            return (Criteria) this;
        }

        public Criteria andPoliceInfoEqualTo(String value) {
            addCriterion("police_info =", value, "policeInfo");
            return (Criteria) this;
        }

        public Criteria andPoliceInfoNotEqualTo(String value) {
            addCriterion("police_info <>", value, "policeInfo");
            return (Criteria) this;
        }

        public Criteria andPoliceInfoGreaterThan(String value) {
            addCriterion("police_info >", value, "policeInfo");
            return (Criteria) this;
        }

        public Criteria andPoliceInfoGreaterThanOrEqualTo(String value) {
            addCriterion("police_info >=", value, "policeInfo");
            return (Criteria) this;
        }

        public Criteria andPoliceInfoLessThan(String value) {
            addCriterion("police_info <", value, "policeInfo");
            return (Criteria) this;
        }

        public Criteria andPoliceInfoLessThanOrEqualTo(String value) {
            addCriterion("police_info <=", value, "policeInfo");
            return (Criteria) this;
        }

        public Criteria andPoliceInfoLike(String value) {
            addCriterion("police_info like", value, "policeInfo");
            return (Criteria) this;
        }

        public Criteria andPoliceInfoNotLike(String value) {
            addCriterion("police_info not like", value, "policeInfo");
            return (Criteria) this;
        }

        public Criteria andPoliceInfoIn(List<String> values) {
            addCriterion("police_info in", values, "policeInfo");
            return (Criteria) this;
        }

        public Criteria andPoliceInfoNotIn(List<String> values) {
            addCriterion("police_info not in", values, "policeInfo");
            return (Criteria) this;
        }

        public Criteria andPoliceInfoBetween(String value1, String value2) {
            addCriterion("police_info between", value1, value2, "policeInfo");
            return (Criteria) this;
        }

        public Criteria andPoliceInfoNotBetween(String value1, String value2) {
            addCriterion("police_info not between", value1, value2, "policeInfo");
            return (Criteria) this;
        }

        public Criteria andCognateIsNull() {
            addCriterion("cognate is null");
            return (Criteria) this;
        }

        public Criteria andCognateIsNotNull() {
            addCriterion("cognate is not null");
            return (Criteria) this;
        }

        public Criteria andCognateEqualTo(String value) {
            addCriterion("cognate =", value, "cognate");
            return (Criteria) this;
        }

        public Criteria andCognateNotEqualTo(String value) {
            addCriterion("cognate <>", value, "cognate");
            return (Criteria) this;
        }

        public Criteria andCognateGreaterThan(String value) {
            addCriterion("cognate >", value, "cognate");
            return (Criteria) this;
        }

        public Criteria andCognateGreaterThanOrEqualTo(String value) {
            addCriterion("cognate >=", value, "cognate");
            return (Criteria) this;
        }

        public Criteria andCognateLessThan(String value) {
            addCriterion("cognate <", value, "cognate");
            return (Criteria) this;
        }

        public Criteria andCognateLessThanOrEqualTo(String value) {
            addCriterion("cognate <=", value, "cognate");
            return (Criteria) this;
        }

        public Criteria andCognateLike(String value) {
            addCriterion("cognate like", value, "cognate");
            return (Criteria) this;
        }

        public Criteria andCognateNotLike(String value) {
            addCriterion("cognate not like", value, "cognate");
            return (Criteria) this;
        }

        public Criteria andCognateIn(List<String> values) {
            addCriterion("cognate in", values, "cognate");
            return (Criteria) this;
        }

        public Criteria andCognateNotIn(List<String> values) {
            addCriterion("cognate not in", values, "cognate");
            return (Criteria) this;
        }

        public Criteria andCognateBetween(String value1, String value2) {
            addCriterion("cognate between", value1, value2, "cognate");
            return (Criteria) this;
        }

        public Criteria andCognateNotBetween(String value1, String value2) {
            addCriterion("cognate not between", value1, value2, "cognate");
            return (Criteria) this;
        }

        public Criteria andGuardianIsNull() {
            addCriterion("guardian is null");
            return (Criteria) this;
        }

        public Criteria andGuardianIsNotNull() {
            addCriterion("guardian is not null");
            return (Criteria) this;
        }

        public Criteria andGuardianEqualTo(String value) {
            addCriterion("guardian =", value, "guardian");
            return (Criteria) this;
        }

        public Criteria andGuardianNotEqualTo(String value) {
            addCriterion("guardian <>", value, "guardian");
            return (Criteria) this;
        }

        public Criteria andGuardianGreaterThan(String value) {
            addCriterion("guardian >", value, "guardian");
            return (Criteria) this;
        }

        public Criteria andGuardianGreaterThanOrEqualTo(String value) {
            addCriterion("guardian >=", value, "guardian");
            return (Criteria) this;
        }

        public Criteria andGuardianLessThan(String value) {
            addCriterion("guardian <", value, "guardian");
            return (Criteria) this;
        }

        public Criteria andGuardianLessThanOrEqualTo(String value) {
            addCriterion("guardian <=", value, "guardian");
            return (Criteria) this;
        }

        public Criteria andGuardianLike(String value) {
            addCriterion("guardian like", value, "guardian");
            return (Criteria) this;
        }

        public Criteria andGuardianNotLike(String value) {
            addCriterion("guardian not like", value, "guardian");
            return (Criteria) this;
        }

        public Criteria andGuardianIn(List<String> values) {
            addCriterion("guardian in", values, "guardian");
            return (Criteria) this;
        }

        public Criteria andGuardianNotIn(List<String> values) {
            addCriterion("guardian not in", values, "guardian");
            return (Criteria) this;
        }

        public Criteria andGuardianBetween(String value1, String value2) {
            addCriterion("guardian between", value1, value2, "guardian");
            return (Criteria) this;
        }

        public Criteria andGuardianNotBetween(String value1, String value2) {
            addCriterion("guardian not between", value1, value2, "guardian");
            return (Criteria) this;
        }

        public Criteria andRewardIsNull() {
            addCriterion("reward is null");
            return (Criteria) this;
        }

        public Criteria andRewardIsNotNull() {
            addCriterion("reward is not null");
            return (Criteria) this;
        }

        public Criteria andRewardEqualTo(String value) {
            addCriterion("reward =", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardNotEqualTo(String value) {
            addCriterion("reward <>", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardGreaterThan(String value) {
            addCriterion("reward >", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardGreaterThanOrEqualTo(String value) {
            addCriterion("reward >=", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardLessThan(String value) {
            addCriterion("reward <", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardLessThanOrEqualTo(String value) {
            addCriterion("reward <=", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardLike(String value) {
            addCriterion("reward like", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardNotLike(String value) {
            addCriterion("reward not like", value, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardIn(List<String> values) {
            addCriterion("reward in", values, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardNotIn(List<String> values) {
            addCriterion("reward not in", values, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardBetween(String value1, String value2) {
            addCriterion("reward between", value1, value2, "reward");
            return (Criteria) this;
        }

        public Criteria andRewardNotBetween(String value1, String value2) {
            addCriterion("reward not between", value1, value2, "reward");
            return (Criteria) this;
        }
    }

    /**
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}