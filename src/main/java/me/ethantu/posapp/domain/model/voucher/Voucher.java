package me.ethantu.posapp.domain.model.voucher;

import me.ethantu.posapp.domain.model.user.User;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 2015/7/15
 * Time: 4:30
 */
@Entity
@Table(name = "voucher")
@DynamicInsert
@DynamicUpdate
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "mobileNo", nullable = true)
    private String mobileNo;

    @Column(name = "password", nullable = true)
    private String password;

    @Column(name = "amount", nullable = true, scale = 2)
    private BigDecimal amount;

    @Column(name = "consume_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date consumedDate;

    @Column(name = "remark")
    private String remark;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "status")
    private int status;                 // 0 初始化 1 已导出 2 正在消费 3 消费成功 4 消费失败

    @ManyToOne(
            targetEntity = User.class,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(name = "owner", foreignKey = @ForeignKey(name ="fk_owner_customer_info"))
    private User owner;

    @Version
    private int version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getConsumedDate() {
        return consumedDate;
    }

    public void setConsumedDate(Date consumedDate) {
        this.consumedDate = consumedDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
