package com.example.easybbsweb.domain.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestRecordExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TestRecordExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(String value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(String value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(String value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(String value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(String value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLike(String value) {
            addCriterion("user_id like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotLike(String value) {
            addCriterion("user_id not like", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<String> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<String> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(String value1, String value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(String value1, String value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andNegCountIsNull() {
            addCriterion("neg_count is null");
            return (Criteria) this;
        }

        public Criteria andNegCountIsNotNull() {
            addCriterion("neg_count is not null");
            return (Criteria) this;
        }

        public Criteria andNegCountEqualTo(Integer value) {
            addCriterion("neg_count =", value, "negCount");
            return (Criteria) this;
        }

        public Criteria andNegCountNotEqualTo(Integer value) {
            addCriterion("neg_count <>", value, "negCount");
            return (Criteria) this;
        }

        public Criteria andNegCountGreaterThan(Integer value) {
            addCriterion("neg_count >", value, "negCount");
            return (Criteria) this;
        }

        public Criteria andNegCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("neg_count >=", value, "negCount");
            return (Criteria) this;
        }

        public Criteria andNegCountLessThan(Integer value) {
            addCriterion("neg_count <", value, "negCount");
            return (Criteria) this;
        }

        public Criteria andNegCountLessThanOrEqualTo(Integer value) {
            addCriterion("neg_count <=", value, "negCount");
            return (Criteria) this;
        }

        public Criteria andNegCountIn(List<Integer> values) {
            addCriterion("neg_count in", values, "negCount");
            return (Criteria) this;
        }

        public Criteria andNegCountNotIn(List<Integer> values) {
            addCriterion("neg_count not in", values, "negCount");
            return (Criteria) this;
        }

        public Criteria andNegCountBetween(Integer value1, Integer value2) {
            addCriterion("neg_count between", value1, value2, "negCount");
            return (Criteria) this;
        }

        public Criteria andNegCountNotBetween(Integer value1, Integer value2) {
            addCriterion("neg_count not between", value1, value2, "negCount");
            return (Criteria) this;
        }

        public Criteria andMedCountIsNull() {
            addCriterion("med_count is null");
            return (Criteria) this;
        }

        public Criteria andMedCountIsNotNull() {
            addCriterion("med_count is not null");
            return (Criteria) this;
        }

        public Criteria andMedCountEqualTo(Integer value) {
            addCriterion("med_count =", value, "medCount");
            return (Criteria) this;
        }

        public Criteria andMedCountNotEqualTo(Integer value) {
            addCriterion("med_count <>", value, "medCount");
            return (Criteria) this;
        }

        public Criteria andMedCountGreaterThan(Integer value) {
            addCriterion("med_count >", value, "medCount");
            return (Criteria) this;
        }

        public Criteria andMedCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("med_count >=", value, "medCount");
            return (Criteria) this;
        }

        public Criteria andMedCountLessThan(Integer value) {
            addCriterion("med_count <", value, "medCount");
            return (Criteria) this;
        }

        public Criteria andMedCountLessThanOrEqualTo(Integer value) {
            addCriterion("med_count <=", value, "medCount");
            return (Criteria) this;
        }

        public Criteria andMedCountIn(List<Integer> values) {
            addCriterion("med_count in", values, "medCount");
            return (Criteria) this;
        }

        public Criteria andMedCountNotIn(List<Integer> values) {
            addCriterion("med_count not in", values, "medCount");
            return (Criteria) this;
        }

        public Criteria andMedCountBetween(Integer value1, Integer value2) {
            addCriterion("med_count between", value1, value2, "medCount");
            return (Criteria) this;
        }

        public Criteria andMedCountNotBetween(Integer value1, Integer value2) {
            addCriterion("med_count not between", value1, value2, "medCount");
            return (Criteria) this;
        }

        public Criteria andPosCountIsNull() {
            addCriterion("pos_count is null");
            return (Criteria) this;
        }

        public Criteria andPosCountIsNotNull() {
            addCriterion("pos_count is not null");
            return (Criteria) this;
        }

        public Criteria andPosCountEqualTo(Integer value) {
            addCriterion("pos_count =", value, "posCount");
            return (Criteria) this;
        }

        public Criteria andPosCountNotEqualTo(Integer value) {
            addCriterion("pos_count <>", value, "posCount");
            return (Criteria) this;
        }

        public Criteria andPosCountGreaterThan(Integer value) {
            addCriterion("pos_count >", value, "posCount");
            return (Criteria) this;
        }

        public Criteria andPosCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("pos_count >=", value, "posCount");
            return (Criteria) this;
        }

        public Criteria andPosCountLessThan(Integer value) {
            addCriterion("pos_count <", value, "posCount");
            return (Criteria) this;
        }

        public Criteria andPosCountLessThanOrEqualTo(Integer value) {
            addCriterion("pos_count <=", value, "posCount");
            return (Criteria) this;
        }

        public Criteria andPosCountIn(List<Integer> values) {
            addCriterion("pos_count in", values, "posCount");
            return (Criteria) this;
        }

        public Criteria andPosCountNotIn(List<Integer> values) {
            addCriterion("pos_count not in", values, "posCount");
            return (Criteria) this;
        }

        public Criteria andPosCountBetween(Integer value1, Integer value2) {
            addCriterion("pos_count between", value1, value2, "posCount");
            return (Criteria) this;
        }

        public Criteria andPosCountNotBetween(Integer value1, Integer value2) {
            addCriterion("pos_count not between", value1, value2, "posCount");
            return (Criteria) this;
        }

        public Criteria andTracePathIsNull() {
            addCriterion("trace_path is null");
            return (Criteria) this;
        }

        public Criteria andTracePathIsNotNull() {
            addCriterion("trace_path is not null");
            return (Criteria) this;
        }

        public Criteria andTracePathEqualTo(String value) {
            addCriterion("trace_path =", value, "tracePath");
            return (Criteria) this;
        }

        public Criteria andTracePathNotEqualTo(String value) {
            addCriterion("trace_path <>", value, "tracePath");
            return (Criteria) this;
        }

        public Criteria andTracePathGreaterThan(String value) {
            addCriterion("trace_path >", value, "tracePath");
            return (Criteria) this;
        }

        public Criteria andTracePathGreaterThanOrEqualTo(String value) {
            addCriterion("trace_path >=", value, "tracePath");
            return (Criteria) this;
        }

        public Criteria andTracePathLessThan(String value) {
            addCriterion("trace_path <", value, "tracePath");
            return (Criteria) this;
        }

        public Criteria andTracePathLessThanOrEqualTo(String value) {
            addCriterion("trace_path <=", value, "tracePath");
            return (Criteria) this;
        }

        public Criteria andTracePathLike(String value) {
            addCriterion("trace_path like", value, "tracePath");
            return (Criteria) this;
        }

        public Criteria andTracePathNotLike(String value) {
            addCriterion("trace_path not like", value, "tracePath");
            return (Criteria) this;
        }

        public Criteria andTracePathIn(List<String> values) {
            addCriterion("trace_path in", values, "tracePath");
            return (Criteria) this;
        }

        public Criteria andTracePathNotIn(List<String> values) {
            addCriterion("trace_path not in", values, "tracePath");
            return (Criteria) this;
        }

        public Criteria andTracePathBetween(String value1, String value2) {
            addCriterion("trace_path between", value1, value2, "tracePath");
            return (Criteria) this;
        }

        public Criteria andTracePathNotBetween(String value1, String value2) {
            addCriterion("trace_path not between", value1, value2, "tracePath");
            return (Criteria) this;
        }

        public Criteria andReportContentIsNull() {
            addCriterion("report_content is null");
            return (Criteria) this;
        }

        public Criteria andReportContentIsNotNull() {
            addCriterion("report_content is not null");
            return (Criteria) this;
        }

        public Criteria andReportContentEqualTo(String value) {
            addCriterion("report_content =", value, "reportContent");
            return (Criteria) this;
        }

        public Criteria andReportContentNotEqualTo(String value) {
            addCriterion("report_content <>", value, "reportContent");
            return (Criteria) this;
        }

        public Criteria andReportContentGreaterThan(String value) {
            addCriterion("report_content >", value, "reportContent");
            return (Criteria) this;
        }

        public Criteria andReportContentGreaterThanOrEqualTo(String value) {
            addCriterion("report_content >=", value, "reportContent");
            return (Criteria) this;
        }

        public Criteria andReportContentLessThan(String value) {
            addCriterion("report_content <", value, "reportContent");
            return (Criteria) this;
        }

        public Criteria andReportContentLessThanOrEqualTo(String value) {
            addCriterion("report_content <=", value, "reportContent");
            return (Criteria) this;
        }

        public Criteria andReportContentLike(String value) {
            addCriterion("report_content like", value, "reportContent");
            return (Criteria) this;
        }

        public Criteria andReportContentNotLike(String value) {
            addCriterion("report_content not like", value, "reportContent");
            return (Criteria) this;
        }

        public Criteria andReportContentIn(List<String> values) {
            addCriterion("report_content in", values, "reportContent");
            return (Criteria) this;
        }

        public Criteria andReportContentNotIn(List<String> values) {
            addCriterion("report_content not in", values, "reportContent");
            return (Criteria) this;
        }

        public Criteria andReportContentBetween(String value1, String value2) {
            addCriterion("report_content between", value1, value2, "reportContent");
            return (Criteria) this;
        }

        public Criteria andReportContentNotBetween(String value1, String value2) {
            addCriterion("report_content not between", value1, value2, "reportContent");
            return (Criteria) this;
        }

        public Criteria andPostionIsNull() {
            addCriterion("postion is null");
            return (Criteria) this;
        }

        public Criteria andPostionIsNotNull() {
            addCriterion("postion is not null");
            return (Criteria) this;
        }

        public Criteria andPostionEqualTo(BigDecimal value) {
            addCriterion("postion =", value, "postion");
            return (Criteria) this;
        }

        public Criteria andPostionNotEqualTo(BigDecimal value) {
            addCriterion("postion <>", value, "postion");
            return (Criteria) this;
        }

        public Criteria andPostionGreaterThan(BigDecimal value) {
            addCriterion("postion >", value, "postion");
            return (Criteria) this;
        }

        public Criteria andPostionGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("postion >=", value, "postion");
            return (Criteria) this;
        }

        public Criteria andPostionLessThan(BigDecimal value) {
            addCriterion("postion <", value, "postion");
            return (Criteria) this;
        }

        public Criteria andPostionLessThanOrEqualTo(BigDecimal value) {
            addCriterion("postion <=", value, "postion");
            return (Criteria) this;
        }

        public Criteria andPostionIn(List<BigDecimal> values) {
            addCriterion("postion in", values, "postion");
            return (Criteria) this;
        }

        public Criteria andPostionNotIn(List<BigDecimal> values) {
            addCriterion("postion not in", values, "postion");
            return (Criteria) this;
        }

        public Criteria andPostionBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("postion between", value1, value2, "postion");
            return (Criteria) this;
        }

        public Criteria andPostionNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("postion not between", value1, value2, "postion");
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