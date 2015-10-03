package com.cgiser.sso.model;

public class ActiveResult {
	 private static final long serialVersionUID = 4959189983143682623L;

	    /** 业务处理状态 */
	    private ActiveEnum businessResult = ActiveEnum.ACTIVE_SUCCESS;

	    /** 业务处理状态的子状态、系统状态 */
	    private String resultCode = "1";

	    /** 激活成功之后，用户对象 */
	    private User businessObject = null;
	    
	    /**
	     * 构造函数
	     * 
	     * @param logonEnum
	     * @param resultCode
	     * @param user
	     */
	    public ActiveResult(ActiveEnum businessResult, int resultCode, User businessObject) {
	        this.businessResult = businessResult;
	        this.resultCode = Integer.toHexString(resultCode);
	        this.businessObject = businessObject;
	    }

	    /**
	     * 检测用户登录是否成功
	     * 
	     * @return
	     */
	    public boolean isSuccess() {
	        return businessResult == ActiveEnum.ACTIVE_SUCCESS;
	    }

	    /**
	     * 检查用户登录是否失败
	     * 
	     * @return
	     */
	    public boolean isFailure() {
	        return businessResult != ActiveEnum.ACTIVE_SUCCESS;
	    }

	    public ActiveEnum getBusinessResult() {
	        return businessResult;
	    }

	    public void setBusinessResult(ActiveEnum businessResult) {
	        this.businessResult = businessResult;
	    }

	    public String getResultCode() {
	        return resultCode;
	    }

	    public void setResultCode(int resultCode) {
	        this.resultCode = Integer.toHexString(resultCode);
	    }

	    public User getBusinessObject() {
	        return businessObject;
	    }

	    public void setBusinessObject(User user) {
	        this.businessObject = user;
	    }
}
