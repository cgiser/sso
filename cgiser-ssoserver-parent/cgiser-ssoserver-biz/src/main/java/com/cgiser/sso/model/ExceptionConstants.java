package com.cgiser.sso.model;

public class ExceptionConstants {

    // 全局常量

    // 业务处理成功
    public final static int SUCCESS = 0x010001;

    // 业务处理失败，原因未知
    public final static int FAILURE = 0xFFFFFF;

    // 网络连接错误
    public final static int NETWORK_CANNOT_LINK = 0x00FFFF;

    // 参数为空
    public final static int PARAMETER_NULL = 0x000FFF;

    // 注册相关的常量
    // 用户昵称已经存在
    public final static int REGISTER_NICK_EXIST = 0x010101;

    // 用户邮箱已经存在
    public final static int REGISTER_EMAIL_EXIST = 0x010102;

    // 用户昵称不符合规范
    public final static int REGISTER_NICK_INVALIDATE = 0x010103;

    // 用户密码不符合规范
    public final static int REGISTER_PASSWORD_INVALIDATE = 0x010104;

    // 用户邮箱不规范
    public final static int REGISTER_EMAIL_INVALIDATE = 0x010105;

    // 用户署名已经存在
    public final static int REGISTER_USERSIGN_EXIST = 0x010106;

    // 用户身份证已经存在
    public final static int REGISTER_IDENTITYCARD_EXIST = 0x010107;

    // 用户署名不符合规范
    public final static int REGISTER_USERSIGN_INVALIDATE = 0x010108;

    // 用户身份证不符合规范
    public final static int REGISTER_IDENTITYCARD_INVALIDATE = 0x010109;

    // 用户真名不符合规范
    public final static int REGISTER_REALNAME_INVALIDATE = 0x010110;

    // 认证申请资料已经存在，等待处理
    public final static int REGISTER_CERTIFIED_INFO_EXIST = 0x010111;

    // 认证申请资料已获得审批
    public final static int REGISTER_CERTIFIED_INFO_PASS = 0x010112;

    // 填写的电话无效
    public final static int REGISTER_TELEPHONE_INVALIDATE = 0x010113;

    // 升级图片版权中心成功
    public final static int APPLY_CERTIFIED_SUCCESS = 0x010114;

    // 升级图片版权中心失败
    public final static int APPLY_CERTIFIED_FAILURE = 0x010115;

    // 用户署名不存在
    public final static int USERSIGN_NOT_EXIST = 0x010116;

    // 用户id不存在
    public final static int USERID_NOT_EXIST = 0x010117;

    // 用户加密id不存在
    public final static int USERIDEN_NOT_EXIST = 0x010118;

    // 用户昵称不存在
    public final static int USERNICK_NOT_EXIST = 0x010119;

    // 用户维客地址不存在
    public final static int USERWIKIURL_NOT_EXIST = 0x010120;

    // 用户角色已经存在
    public final static int USER_ROLE_EXIST = 0x010121;

    // 注册的用户IP已经被封
    public final static int REGISTER_USERIP_BLOCK = 0x010122;

    // 站外站点信息不全
    public final static int REGISTER_OUTERSITE_INFO_NULL = 0x010123;

    // 登陆相关的常量
    // 邮箱不存在,66049
    public final static int LOGON_USER_NOT_EXIST = 0x010201;

    // 密码不正确
    public final static int LOGON_PASSWORD_WRONG = 0x010202;

    // 用户已经被删除或者被冻结
    public final static int LOGON_USER_INVALIDATE = 0x010203;

    // 没有输入邮箱
    public final static int LOGON_EMAIL_EMPTY = 0x010204;

    // add by zhaoc 登录的用户IP已经被封
    public final static int LOGON_USERIP_BLOCK = 0x010205;

    // 伪登陆相关常量
    // 用户加密ID为空
    public final static int FAKE_LOGON_ID_EN_EMPTY = 0x010301;

    // 用户ID为不存在
    public final static int FAKE_LOGON_ID_NOT_EXIST = 0x010302;

    // 用户管理相关常量
    // 用户iden为空
    public final static int USER_ADMIN_USER_IDEN_EMPTY = 0x010401;

    // 密码为空
    public final static int USER_ADMIN_PASSWD_EMPTY = 0x010402;

    // 新密码和确认密码不匹配
    public final static int USER_ADMIN_PASSWD_NOTMATCH = 0x010403;

    // 密码太短
    public final static int USER_ADMIN_PASSWD_TOOSHORT = 0x010404;

    // 密码太长
    public final static int USER_ADMIN_PASSWD_TOOLONG = 0x010405;

    // 性别太长
    public final static int USER_ADMIN_GENDER_TOOLONG = 0x010406;

    // 地址太长
    public final static int USER_ADMIN_ADDRESS_TOOLONG = 0x010407;

    // 用户真名太长
    public final static int USER_ADMIN_REALNAME_TOOLONG = 0x010408;

    // 电话太长
    public final static int USER_ADMIN_TELEPHONE_TOOLONG = 0x010409;

    // 居住城市太长
    public final static int USER_ADMIN_CITY_TOOLONG = 0x01040A;

    // 个性签名太长
    public final static int USER_ADMIN_USERINTRO_TOOLONG = 0x01040B;

    // 维客地址已经设置
    public final static int USER_ADMIN_WIKIURL_ALREADY_SET = 0x01040C;

    // 维客地址为空
    public final static int USER_ADMIN_WIKIURL_EMPTY = 0x01040D;

    // 维客地址太短
    public final static int USER_ADMIN_WIKIURL_TOOSHORT = 0x01040E;

    // 维客地址太长
    public final static int USER_ADMIN_WIKIURL_TOOLONG = 0x01040F;

    // 维客地址含有非法字符
    public final static int USER_ADMIN_WIKIURL_WRONGCHAR = 0x010411;

    // 维客地址已经存在
    public final static int USER_ADMIN_WIKIURL_ALREADY_EXIST = 0x010412;

    // 用户头像地址太长
    public final static int USER_ADMIN_USERPORTRAITURL_TOOLONG = 0x010413;

    // 用户头像地址为空
    public final static int USER_ADMIN_USERPORTRAITURL_EMPTY = 0x010414;

    // 找回密码的时限已经过期
    public final static int USER_ADMIN_GETPASS_TIME_INVALID = 0x010415;

    // 找回密码失败
    public final static int USER_ADMIN_GETPASS_FAILED = 0x010416;

    // 操作理由为空
    public final static int USER_ADMIN_REASON_EMPTY = 0x010417;

    // 操作理由太长
    public final static int USER_ADMIN_REASON_TOOLONG = 0x010418;

    // 是维吧吧主
    public final static int USER_IS_BAR_MANAGER = 0x010419;

    // 不是维吧吧主
    public final static int USER_ISNOT_BAR_MANAGER = 0x010420;

    // 是用户的好友
    public final static int USER_IS_FRIEND = 0x010421;

    // 不是用户好友
    public final static int USER_ISNOT_FRIEND = 0x010422;

    // 是分类管理员
    public final static int USER_IS_CATEGORY_MANAGER = 0x010423;

    // 不是分类管理员
    public final static int USER_ISNOT_CATEGORY_MANAGER = 0x010424;

    // 值太长
    public final static int VALUE_TOOLONG = 0x010425;

    // 记录值已经存在
    public final static int EXIST_RECORD_VALUE = 0x010426;

    // 记录值已经存在
    public final static int NOT_EXIST_RECORD_VALUE = 0x010427;

    // 没有指定角色
    public final static int NO_ROLE = 0x010428;

    // 用户没有足够的可用信用
    public final static int CURRENT_CREDIT_NOT_ENOUGH = 0x010429;

    // 用户没有足够的可用积分
    public final static int CURRENT_POINT_NOT_ENOUGH = 0x010430;

    // 用户没有足够的冻结信用
    public final static int FROZEN_CREDIT_NOT_ENOUGH = 0x010431;

    // 用户没有足够的冻结积分
    public final static int FROZEN_POINT_NOT_ENOUGH = 0x010432;

    // 积分与信用类型错误
    public final static int CREDIT_POINT_TYPE_ERROR = 0x010433;

    // 用户不存在
    public final static int USER_NOT_EXIST = 0x010435;

    // 好友不存在
    public final static int FRIEND_NOT_EXIST = 0x010436;

    // 已经是好友
    public final static int ALREADY_FRIEND = 0x010437;

    // 分类名不存在
    public final static int CATEGORY_NAME_NOT_EXIST = 0x010438;

    // 词条ID不存在
    public final static int DOCID_NOT_EXIST = 0x010439;

    // 不可用的信用值
    public final static int ILLEGAL_CREDIT_SCORE = 0x010440;

    // 错误的转移类型
    public final static int ILLEGAL_TRANSFER_TYPE = 0x010441;

    // add by liaoxiandong 20111104 用户专业为空
    public final static int USER_SUBJECT_IS_NULL = 0x010442;

    // add by zhangjuanli 2011-12-16 分类管理员信息模型为空
    public final static int THEME_CATEGORY_MANAGERINFO_IS_NULL = 0x010443;

    // add by zhangjuanli 2011-12-16 分类管理员ID，分类信息为空
    public final static int MANAGER_ID_CATEGORY_INFO_IS_NULL = 0x010444;
    
    //登录的手机号没有注册
    public final static int LOGON_MOBILE_NOT_EXIST = 0x010445;
    
    //注册的手机号无效
    public final static int REGISTER_PHONE_INVALIDATE = 0x010446;

    //第三方账户不允许操作
    public final static int NOT_SUPPORT_THIRDPART_ACCOUNT = 0x010447;

    public static void main(String[] args) {
        System.out.println(Integer.toHexString(66049));
    }
}
