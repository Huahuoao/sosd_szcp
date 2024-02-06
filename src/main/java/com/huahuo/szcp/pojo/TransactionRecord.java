package com.huahuo.szcp.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName transaction_record
 */
@TableName(value ="transaction_record")
@Data
public class TransactionRecord implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品名字
     */
    @TableField(value = "product_name")
    private String productName;

    /**
     * 交易时间
     */
    @TableField(value = "transaction_time")
    private LocalDateTime transactionTime;

    /**
     * 交易单号
     */
    @TableField(value = "transaction_number")
    private String transactionNumber;

    /**
     * 买方ID
     */
    @TableField(value = "buyer_id")
    private Integer buyerId;

    /**
     * 卖方ID
     */
    @TableField(value = "seller_id")
    private Integer sellerId;

    /**
     * 交易额
     */
    @TableField(value = "transaction_amount")
    private BigDecimal transactionAmount;

    /**
     * 创建时间
     */
    @TableField(value = "created_at")
    private LocalDateTime createdAt;

    /**
     * 修改时间
     */
    @TableField(value = "updated_at")
    private LocalDateTime updatedAt;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TransactionRecord other = (TransactionRecord) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProductName() == null ? other.getProductName() == null : this.getProductName().equals(other.getProductName()))
            && (this.getTransactionTime() == null ? other.getTransactionTime() == null : this.getTransactionTime().equals(other.getTransactionTime()))
            && (this.getTransactionNumber() == null ? other.getTransactionNumber() == null : this.getTransactionNumber().equals(other.getTransactionNumber()))
            && (this.getBuyerId() == null ? other.getBuyerId() == null : this.getBuyerId().equals(other.getBuyerId()))
            && (this.getSellerId() == null ? other.getSellerId() == null : this.getSellerId().equals(other.getSellerId()))
            && (this.getTransactionAmount() == null ? other.getTransactionAmount() == null : this.getTransactionAmount().equals(other.getTransactionAmount()))
            && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
            && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProductName() == null) ? 0 : getProductName().hashCode());
        result = prime * result + ((getTransactionTime() == null) ? 0 : getTransactionTime().hashCode());
        result = prime * result + ((getTransactionNumber() == null) ? 0 : getTransactionNumber().hashCode());
        result = prime * result + ((getBuyerId() == null) ? 0 : getBuyerId().hashCode());
        result = prime * result + ((getSellerId() == null) ? 0 : getSellerId().hashCode());
        result = prime * result + ((getTransactionAmount() == null) ? 0 : getTransactionAmount().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", productName=").append(productName);
        sb.append(", transactionTime=").append(transactionTime);
        sb.append(", transactionNumber=").append(transactionNumber);
        sb.append(", buyerId=").append(buyerId);
        sb.append(", sellerId=").append(sellerId);
        sb.append(", transactionAmount=").append(transactionAmount);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}