package com.jxzy.AppMigration.NavigationApp.entity;
/**
 *
 * <pre>
 * 项目名称：九星智元机器人项目
 * 类名称：Point
 * 类描述：测算当前GPS是否在描点内
 * 创建人：曲绍备
 * 创建时间：2019年7月16日 下午2:21:46
 * 修改人：曲绍备
 * 修改时间：2019年7月16日 下午2:21:46
 * 修改备注：
 * @version
 * </pre>
 */
public class Point {
    private Double x;
    private Double y;
    public Point (Double x , Double y) {
        this.x = x;
        this.y = y;
    }
    public Double getX() {
        return x;
    }
    public void setX(Double x) {
        this.x = x;
    }
    public Double getY() {
        return y;
    }
    public void setY(Double y) {
        this.y = y;
    }
}
