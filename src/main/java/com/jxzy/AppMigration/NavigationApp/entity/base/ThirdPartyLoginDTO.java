package com.jxzy.AppMigration.NavigationApp.entity.base;

import com.jxzy.AppMigration.NavigationApp.entity.SysGuideAppUsers;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @Author zhang
 * @Date 2022/7/22 10:25
 */
@Data
public class ThirdPartyLoginDTO {

    private String wechatId;

    //    @Size(min = 20)
    private String qq;

    private String appleId;

    private String appleToken;

    public SysGuideAppUsers convertToUser() {
        UserDtoConvert userDtoConvert = new UserDtoConvert();
        return userDtoConvert.doForward(this);
    }

    private static class UserDtoConvert extends BaseDtoConvert<ThirdPartyLoginDTO, SysGuideAppUsers> {
        @Override
        protected SysGuideAppUsers doForward(ThirdPartyLoginDTO loginDTO) {
            SysGuideAppUsers user = new SysGuideAppUsers();
            BeanUtils.copyProperties(loginDTO, user);
            return user;
        }

        @Override
        protected ThirdPartyLoginDTO doBackward(SysGuideAppUsers user) {
            return null;
        }
    }



}
