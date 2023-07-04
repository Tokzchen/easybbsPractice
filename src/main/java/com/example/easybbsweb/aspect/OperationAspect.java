package com.example.easybbsweb.aspect;

import cn.hutool.core.util.ArrayUtil;
import com.example.easybbsweb.anotation.GlobalInterceptor;
import com.example.easybbsweb.anotation.VerifyParam;
import com.example.easybbsweb.exception.BusinessException;
import com.example.easybbsweb.exception.SystemException;
import com.example.easybbsweb.utils.TokenUtil;
import com.example.easybbsweb.utils.VerifyParamUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

@Aspect
@Component
public class OperationAspect {

    private static final String[] TYPE_ARR={"java.lang.String","java.lang.Integer","java.lang.Long"};
    @Pointcut("@annotation(com.example.easybbsweb.anotation.GlobalInterceptor)")
    public void requestInterceptor(){};

    @Around("requestInterceptor()")
    public Object interceptorDo(ProceedingJoinPoint proceedingJoinPoint){
        try {
            //如果这个注解是在方法上，获取到的就是method所属的方法的那个对象，比如我们加在
            //service层的某个方法上，获取的就是这个service对象,不是class对象
            Object target = proceedingJoinPoint.getTarget();
            //一般是某个方法的参数
            Object[] args = proceedingJoinPoint.getArgs();
            //方法的名字
            String methodName = proceedingJoinPoint.getSignature().getName();
            //获取这个对象的简单类名
            String simpleName = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
            //获取这个对象的全类名
            String fullName = proceedingJoinPoint.getSignature().getDeclaringTypeName();

            Class<?>[] parameterTypes = ((MethodSignature) (proceedingJoinPoint.getSignature())).getMethod().getParameterTypes();
            Method method = target.getClass().getMethod(methodName, parameterTypes);
            GlobalInterceptor interceptor = method.getAnnotation(GlobalInterceptor.class);
            if(interceptor.checkIsLogin()){
                //检测token
                int tokenIndex=-1;
                Parameter[] parameters = method.getParameters();
                for(int i=0;i<parameters.length;i++){
                    if(parameters[i].getName().equals("token")){
                        tokenIndex=i;
                        break;
                    }
                }
                if(tokenIndex==-1){
                    throw new SystemException("压根没接收请求头的token,老兄");
                }
                String token= (String) args[tokenIndex];
                //获取到token开始检验
                boolean verify = TokenUtil.verify(token);
                if(!verify){
                    throw new BusinessException("异常");
                }
            }
            if(interceptor.checkParameters()){
                //检查参数
                validateParams(method,args);
            }
            //如果不带参数则使用原来的参数，也可以使用新的Object[]替换原来的参数
            Object proceed = proceedingJoinPoint.proceed();
            return proceed;

        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            throw new BusinessException();
        }catch (Throwable t){
            throw new BusinessException();
        }
    }
    private void validateParams(Method m,Object[] args){
        //可以拿到所有的参数对象，但只有参数名和index（位置)
        Parameter[] parameters = m.getParameters();
        for(int i=0;i<parameters.length;i++){
            Object value=args[i];
            //可以通过这个parameter对象获取这个参数上的注解
            Parameter parameter=parameters[i];
            VerifyParam verifyParam = parameter.getAnnotation(VerifyParam.class);
            //因为这里不是通过切面进入的(切面进入的是某个方法),而是通过遍历，因此
            if(verifyParam==null){
                continue;
            }
            //如果是普通类型的参数
            if(ArrayUtil.contains(TYPE_ARR,parameter.getParameterizedType().getTypeName())){
                checkValue(value,verifyParam);
            }else{
                //如果传的是对象,则通过反射获取所有属性再进行校验
            }


        }

    }

    private void checkValue(Object value,VerifyParam verifyParam){

        Boolean isEmpty=value==null|| StringUtils.hasText(value.toString());
        Integer length=value==null?0:value.toString().length();
        //校验为空
        if(isEmpty&&verifyParam.required()){
            throw new BusinessException("异常");
        }
        //校验长度
        if(!isEmpty&&(verifyParam.max()!=-1&&verifyParam.max()<length||verifyParam.min()!=-1&&verifyParam.min()>length)){
            throw new BusinessException("异常");
        }

        //校验正则
        if(!isEmpty&&StringUtils.hasText(verifyParam.regex().getRegex())
                && !VerifyParamUtil.checkValue(verifyParam.regex(),String.valueOf(value))){
            throw new BusinessException("异常");
        }
    }

    private void checkObjectValue(Object value,VerifyParam verifyParam){
        try{
            //通过getDeclaredFields()方法获取对象类中的所有属性（含私有）
            Field[] fields = value.getClass().getDeclaredFields();
            for (Field field : fields) {
                //设置允许通过反射访问私有变量
                field.setAccessible(true);
                VerifyParam ver = field.getAnnotation(VerifyParam.class);
                //获取字段的值
                Object filedValue = field.get(value);
                checkValue(value,ver);
                //获取字段属性名称
                String name = field.getName();
                //其他自定义操作
            }
        }
        catch (Exception ex){
            //处理异常
        }


    }



}
