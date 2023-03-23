package com.example.easybbsweb.domain.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserMainExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public UserMainExample() {
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

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andAidIdIsNull() {
            addCriterion("aid_id is null");
            return (Criteria) this;
        }

        public Criteria andAidIdIsNotNull() {
            addCriterion("aid_id is not null");
            return (Criteria) this;
        }

        public Criteria andAidIdEqualTo(Integer value) {
            addCriterion("aid_id =", value, "aidId");
            return (Criteria) this;
        }

        public Criteria andAidIdNotEqualTo(Integer value) {
            addCriterion("aid_id <>", value, "aidId");
            return (Criteria) this;
        }

        public Criteria andAidIdGreaterThan(Integer value) {
            addCriterion("aid_id >", value, "aidId");
            return (Criteria) this;
        }

        public Criteria andAidIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("aid_id >=", value, "aidId");
            return (Criteria) this;
        }

        public Criteria andAidIdLessThan(Integer value) {
            addCriterion("aid_id <", value, "aidId");
            return (Criteria) this;
        }

        public Criteria andAidIdLessThanOrEqualTo(Integer value) {
            addCriterion("aid_id <=", value, "aidId");
            return (Criteria) this;
        }

        public Criteria andAidIdIn(List<Integer> values) {
            addCriterion("aid_id in", values, "aidId");
            return (Criteria) this;
        }

        public Criteria andAidIdNotIn(List<Integer> values) {
            addCriterion("aid_id not in", values, "aidId");
            return (Criteria) this;
        }

        public Criteria andAidIdBetween(Integer value1, Integer value2) {
            addCriterion("aid_id between", value1, value2, "aidId");
            return (Criteria) this;
        }

        public Criteria andAidIdNotBetween(Integer value1, Integer value2) {
            addCriterion("aid_id not between", value1, value2, "aidId");
            return (Criteria) this;
        }

        public Criteria andLawyerIdIsNull() {
            addCriterion("lawyer_id is null");
            return (Criteria) this;
        }

        public Criteria andLawyerIdIsNotNull() {
            addCriterion("lawyer_id is not null");
            return (Criteria) this;
        }

        public Criteria andLawyerIdEqualTo(Integer value) {
            addCriterion("lawyer_id =", value, "lawyerId");
            return (Criteria) this;
        }

        public Criteria andLawyerIdNotEqualTo(Integer value) {
            addCriterion("lawyer_id <>", value, "lawyerId");
            return (Criteria) this;
        }

        public Criteria andLawyerIdGreaterThan(Integer value) {
            addCriterion("lawyer_id >", value, "lawyerId");
            return (Criteria) this;
        }

        public Criteria andLawyerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("lawyer_id >=", value, "lawyerId");
            return (Criteria) this;
        }

        public Criteria andLawyerIdLessThan(Integer value) {
            addCriterion("lawyer_id <", value, "lawyerId");
            return (Criteria) this;
        }

        public Criteria andLawyerIdLessThanOrEqualTo(Integer value) {
            addCriterion("lawyer_id <=", value, "lawyerId");
            return (Criteria) this;
        }

        public Criteria andLawyerIdIn(List<Integer> values) {
            addCriterion("lawyer_id in", values, "lawyerId");
            return (Criteria) this;
        }

        public Criteria andLawyerIdNotIn(List<Integer> values) {
            addCriterion("lawyer_id not in", values, "lawyerId");
            return (Criteria) this;
        }

        public Criteria andLawyerIdBetween(Integer value1, Integer value2) {
            addCriterion("lawyer_id between", value1, value2, "lawyerId");
            return (Criteria) this;
        }

        public Criteria andLawyerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("lawyer_id not between", value1, value2, "lawyerId");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andEmailCodeIsNull() {
            addCriterion("email_code is null");
            return (Criteria) this;
        }

        public Criteria andEmailCodeIsNotNull() {
            addCriterion("email_code is not null");
            return (Criteria) this;
        }

        public Criteria andEmailCodeEqualTo(String value) {
            addCriterion("email_code =", value, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeNotEqualTo(String value) {
            addCriterion("email_code <>", value, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeGreaterThan(String value) {
            addCriterion("email_code >", value, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeGreaterThanOrEqualTo(String value) {
            addCriterion("email_code >=", value, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeLessThan(String value) {
            addCriterion("email_code <", value, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeLessThanOrEqualTo(String value) {
            addCriterion("email_code <=", value, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeLike(String value) {
            addCriterion("email_code like", value, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeNotLike(String value) {
            addCriterion("email_code not like", value, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeIn(List<String> values) {
            addCriterion("email_code in", values, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeNotIn(List<String> values) {
            addCriterion("email_code not in", values, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeBetween(String value1, String value2) {
            addCriterion("email_code between", value1, value2, "emailCode");
            return (Criteria) this;
        }

        public Criteria andEmailCodeNotBetween(String value1, String value2) {
            addCriterion("email_code not between", value1, value2, "emailCode");
            return (Criteria) this;
        }

        public Criteria andCheckCodeIsNull() {
            addCriterion("check_code is null");
            return (Criteria) this;
        }

        public Criteria andCheckCodeIsNotNull() {
            addCriterion("check_code is not null");
            return (Criteria) this;
        }

        public Criteria andCheckCodeEqualTo(String value) {
            addCriterion("check_code =", value, "checkCode");
            return (Criteria) this;
        }

        public Criteria andCheckCodeNotEqualTo(String value) {
            addCriterion("check_code <>", value, "checkCode");
            return (Criteria) this;
        }

        public Criteria andCheckCodeGreaterThan(String value) {
            addCriterion("check_code >", value, "checkCode");
            return (Criteria) this;
        }

        public Criteria andCheckCodeGreaterThanOrEqualTo(String value) {
            addCriterion("check_code >=", value, "checkCode");
            return (Criteria) this;
        }

        public Criteria andCheckCodeLessThan(String value) {
            addCriterion("check_code <", value, "checkCode");
            return (Criteria) this;
        }

        public Criteria andCheckCodeLessThanOrEqualTo(String value) {
            addCriterion("check_code <=", value, "checkCode");
            return (Criteria) this;
        }

        public Criteria andCheckCodeLike(String value) {
            addCriterion("check_code like", value, "checkCode");
            return (Criteria) this;
        }

        public Criteria andCheckCodeNotLike(String value) {
            addCriterion("check_code not like", value, "checkCode");
            return (Criteria) this;
        }

        public Criteria andCheckCodeIn(List<String> values) {
            addCriterion("check_code in", values, "checkCode");
            return (Criteria) this;
        }

        public Criteria andCheckCodeNotIn(List<String> values) {
            addCriterion("check_code not in", values, "checkCode");
            return (Criteria) this;
        }

        public Criteria andCheckCodeBetween(String value1, String value2) {
            addCriterion("check_code between", value1, value2, "checkCode");
            return (Criteria) this;
        }

        public Criteria andCheckCodeNotBetween(String value1, String value2) {
            addCriterion("check_code not between", value1, value2, "checkCode");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIsNull() {
            addCriterion("last_update_time is null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIsNotNull() {
            addCriterion("last_update_time is not null");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeEqualTo(Date value) {
            addCriterion("last_update_time =", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotEqualTo(Date value) {
            addCriterion("last_update_time <>", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThan(Date value) {
            addCriterion("last_update_time >", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("last_update_time >=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThan(Date value) {
            addCriterion("last_update_time <", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("last_update_time <=", value, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeIn(List<Date> values) {
            addCriterion("last_update_time in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotIn(List<Date> values) {
            addCriterion("last_update_time not in", values, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("last_update_time between", value1, value2, "lastUpdateTime");
            return (Criteria) this;
        }

        public Criteria andLastUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("last_update_time not between", value1, value2, "lastUpdateTime");
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