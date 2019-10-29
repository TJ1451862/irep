package cn.edu.whu.irlab.irep.base.entity.system;

import lombok.Data;

@Data
public class LoginCount {
    private Integer id;
    private Integer total;

    public void setId(Integer id){
        this.id = id;
    }

    public  void setTotal(Integer total){
        this.total = total;
    }

    public Integer getId(){
        return this.id;
    }

    public  Integer getTotal(){
        return this.total;
    }
}
