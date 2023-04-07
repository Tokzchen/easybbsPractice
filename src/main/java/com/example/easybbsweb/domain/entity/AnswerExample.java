package com.example.easybbsweb.domain.entity;

import java.util.ArrayList;
import java.util.List;

public class AnswerExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AnswerExample() {
        oredCriteria = new ArrayList<>();
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

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
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

        public Criteria andAnsIdIsNull() {
            addCriterion("ans_id is null");
            return (Criteria) this;
        }

        public Criteria andAnsIdIsNotNull() {
            addCriterion("ans_id is not null");
            return (Criteria) this;
        }

        public Criteria andAnsIdEqualTo(Integer value) {
            addCriterion("ans_id =", value, "ansId");
            return (Criteria) this;
        }

        public Criteria andAnsIdNotEqualTo(Integer value) {
            addCriterion("ans_id <>", value, "ansId");
            return (Criteria) this;
        }

        public Criteria andAnsIdGreaterThan(Integer value) {
            addCriterion("ans_id >", value, "ansId");
            return (Criteria) this;
        }

        public Criteria andAnsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ans_id >=", value, "ansId");
            return (Criteria) this;
        }

        public Criteria andAnsIdLessThan(Integer value) {
            addCriterion("ans_id <", value, "ansId");
            return (Criteria) this;
        }

        public Criteria andAnsIdLessThanOrEqualTo(Integer value) {
            addCriterion("ans_id <=", value, "ansId");
            return (Criteria) this;
        }

        public Criteria andAnsIdIn(List<Integer> values) {
            addCriterion("ans_id in", values, "ansId");
            return (Criteria) this;
        }

        public Criteria andAnsIdNotIn(List<Integer> values) {
            addCriterion("ans_id not in", values, "ansId");
            return (Criteria) this;
        }

        public Criteria andAnsIdBetween(Integer value1, Integer value2) {
            addCriterion("ans_id between", value1, value2, "ansId");
            return (Criteria) this;
        }

        public Criteria andAnsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ans_id not between", value1, value2, "ansId");
            return (Criteria) this;
        }

        public Criteria andQueIdIsNull() {
            addCriterion("que_id is null");
            return (Criteria) this;
        }

        public Criteria andQueIdIsNotNull() {
            addCriterion("que_id is not null");
            return (Criteria) this;
        }

        public Criteria andQueIdEqualTo(Integer value) {
            addCriterion("que_id =", value, "queId");
            return (Criteria) this;
        }

        public Criteria andQueIdNotEqualTo(Integer value) {
            addCriterion("que_id <>", value, "queId");
            return (Criteria) this;
        }

        public Criteria andQueIdGreaterThan(Integer value) {
            addCriterion("que_id >", value, "queId");
            return (Criteria) this;
        }

        public Criteria andQueIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("que_id >=", value, "queId");
            return (Criteria) this;
        }

        public Criteria andQueIdLessThan(Integer value) {
            addCriterion("que_id <", value, "queId");
            return (Criteria) this;
        }

        public Criteria andQueIdLessThanOrEqualTo(Integer value) {
            addCriterion("que_id <=", value, "queId");
            return (Criteria) this;
        }

        public Criteria andQueIdIn(List<Integer> values) {
            addCriterion("que_id in", values, "queId");
            return (Criteria) this;
        }

        public Criteria andQueIdNotIn(List<Integer> values) {
            addCriterion("que_id not in", values, "queId");
            return (Criteria) this;
        }

        public Criteria andQueIdBetween(Integer value1, Integer value2) {
            addCriterion("que_id between", value1, value2, "queId");
            return (Criteria) this;
        }

        public Criteria andQueIdNotBetween(Integer value1, Integer value2) {
            addCriterion("que_id not between", value1, value2, "queId");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andEffectIsNull() {
            addCriterion("effect is null");
            return (Criteria) this;
        }

        public Criteria andEffectIsNotNull() {
            addCriterion("effect is not null");
            return (Criteria) this;
        }

        public Criteria andEffectEqualTo(Byte value) {
            addCriterion("effect =", value, "effect");
            return (Criteria) this;
        }

        public Criteria andEffectNotEqualTo(Byte value) {
            addCriterion("effect <>", value, "effect");
            return (Criteria) this;
        }

        public Criteria andEffectGreaterThan(Byte value) {
            addCriterion("effect >", value, "effect");
            return (Criteria) this;
        }

        public Criteria andEffectGreaterThanOrEqualTo(Byte value) {
            addCriterion("effect >=", value, "effect");
            return (Criteria) this;
        }

        public Criteria andEffectLessThan(Byte value) {
            addCriterion("effect <", value, "effect");
            return (Criteria) this;
        }

        public Criteria andEffectLessThanOrEqualTo(Byte value) {
            addCriterion("effect <=", value, "effect");
            return (Criteria) this;
        }

        public Criteria andEffectIn(List<Byte> values) {
            addCriterion("effect in", values, "effect");
            return (Criteria) this;
        }

        public Criteria andEffectNotIn(List<Byte> values) {
            addCriterion("effect not in", values, "effect");
            return (Criteria) this;
        }

        public Criteria andEffectBetween(Byte value1, Byte value2) {
            addCriterion("effect between", value1, value2, "effect");
            return (Criteria) this;
        }

        public Criteria andEffectNotBetween(Byte value1, Byte value2) {
            addCriterion("effect not between", value1, value2, "effect");
            return (Criteria) this;
        }

        public Criteria andAppendIsNull() {
            addCriterion("append is null");
            return (Criteria) this;
        }

        public Criteria andAppendIsNotNull() {
            addCriterion("append is not null");
            return (Criteria) this;
        }

        public Criteria andAppendEqualTo(String value) {
            addCriterion("append =", value, "append");
            return (Criteria) this;
        }

        public Criteria andAppendNotEqualTo(String value) {
            addCriterion("append <>", value, "append");
            return (Criteria) this;
        }

        public Criteria andAppendGreaterThan(String value) {
            addCriterion("append >", value, "append");
            return (Criteria) this;
        }

        public Criteria andAppendGreaterThanOrEqualTo(String value) {
            addCriterion("append >=", value, "append");
            return (Criteria) this;
        }

        public Criteria andAppendLessThan(String value) {
            addCriterion("append <", value, "append");
            return (Criteria) this;
        }

        public Criteria andAppendLessThanOrEqualTo(String value) {
            addCriterion("append <=", value, "append");
            return (Criteria) this;
        }

        public Criteria andAppendLike(String value) {
            addCriterion("append like", value, "append");
            return (Criteria) this;
        }

        public Criteria andAppendNotLike(String value) {
            addCriterion("append not like", value, "append");
            return (Criteria) this;
        }

        public Criteria andAppendIn(List<String> values) {
            addCriterion("append in", values, "append");
            return (Criteria) this;
        }

        public Criteria andAppendNotIn(List<String> values) {
            addCriterion("append not in", values, "append");
            return (Criteria) this;
        }

        public Criteria andAppendBetween(String value1, String value2) {
            addCriterion("append between", value1, value2, "append");
            return (Criteria) this;
        }

        public Criteria andAppendNotBetween(String value1, String value2) {
            addCriterion("append not between", value1, value2, "append");
            return (Criteria) this;
        }
    }

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