package com.huahuo.szcp.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 藏品信息表
 *
 * @TableName collection
 */
@TableName(value = "collection")
@Data
public class Collection implements Serializable {
    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 藏品名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 图片链接
     */
    @TableField(value = "image_url")
    private String imageUrl;

    /**
     * 藏品介绍
     */
    @TableField(value = "description")
    private String description;

    /**
     * 价格
     */
    @TableField(value = "price")
    private BigDecimal price;

    /**
     * 份数
     */
    @TableField(value = "quantity")
    private Integer quantity;

    /**
     * 分类
     */
    @TableField(value = "category")
    private String category;

    /**
     * 制作者
     */
    @TableField(value = "creator")
    private Integer creator;

    /**
     * 拥有者
     */
    @TableField(value = "owner")
    private Integer owner;

    /**
     * 区块链地址
     */
    @TableField(value = "blockchain_address")
    private String blockchainAddress;

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

    /**
     * 链上地址
     */
    @TableField(value = "chain_address")
    private String chainAddress;

    /**
     * 是否上链
     */
    @TableField(value = "is_on_chain")
    private Boolean isOnChain;

    /**
     *
     */
    @TableField(value = "on_chain_time")
    private LocalDateTime onChainTime;

    /**
     * 喜欢数
     */
    @TableField(value = "like_num")
    private Integer likeNum;

    /**
     * 热度
     */
    @TableField(value = "hot_value")
    private Integer hotValue;

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
        Collection other = (Collection) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getImageUrl() == null ? other.getImageUrl() == null : this.getImageUrl().equals(other.getImageUrl()))
                && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
                && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
                && (this.getQuantity() == null ? other.getQuantity() == null : this.getQuantity().equals(other.getQuantity()))
                && (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
                && (this.getCreator() == null ? other.getCreator() == null : this.getCreator().equals(other.getCreator()))
                && (this.getOwner() == null ? other.getOwner() == null : this.getOwner().equals(other.getOwner()))
                && (this.getBlockchainAddress() == null ? other.getBlockchainAddress() == null : this.getBlockchainAddress().equals(other.getBlockchainAddress()))
                && (this.getCreatedAt() == null ? other.getCreatedAt() == null : this.getCreatedAt().equals(other.getCreatedAt()))
                && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null : this.getUpdatedAt().equals(other.getUpdatedAt()))
                && (this.getChainAddress() == null ? other.getChainAddress() == null : this.getChainAddress().equals(other.getChainAddress()))
                && (this.getIsOnChain() == null ? other.getIsOnChain() == null : this.getIsOnChain().equals(other.getIsOnChain()))
                && (this.getOnChainTime() == null ? other.getOnChainTime() == null : this.getOnChainTime().equals(other.getOnChainTime()))
                && (this.getLikeNum() == null ? other.getLikeNum() == null : this.getLikeNum().equals(other.getLikeNum()))
                && (this.getHotValue() == null ? other.getHotValue() == null : this.getHotValue().equals(other.getHotValue()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getImageUrl() == null) ? 0 : getImageUrl().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getQuantity() == null) ? 0 : getQuantity().hashCode());
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
        result = prime * result + ((getCreator() == null) ? 0 : getCreator().hashCode());
        result = prime * result + ((getOwner() == null) ? 0 : getOwner().hashCode());
        result = prime * result + ((getBlockchainAddress() == null) ? 0 : getBlockchainAddress().hashCode());
        result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
        result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
        result = prime * result + ((getChainAddress() == null) ? 0 : getChainAddress().hashCode());
        result = prime * result + ((getIsOnChain() == null) ? 0 : getIsOnChain().hashCode());
        result = prime * result + ((getOnChainTime() == null) ? 0 : getOnChainTime().hashCode());
        result = prime * result + ((getLikeNum() == null) ? 0 : getLikeNum().hashCode());
        result = prime * result + ((getHotValue() == null) ? 0 : getHotValue().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", imageUrl=").append(imageUrl);
        sb.append(", description=").append(description);
        sb.append(", price=").append(price);
        sb.append(", quantity=").append(quantity);
        sb.append(", category=").append(category);
        sb.append(", creator=").append(creator);
        sb.append(", owner=").append(owner);
        sb.append(", blockchainAddress=").append(blockchainAddress);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", chainAddress=").append(chainAddress);
        sb.append(", isOnChain=").append(isOnChain);
        sb.append(", onChainTime=").append(onChainTime);
        sb.append(", likeNum=").append(likeNum);
        sb.append(", hotValue=").append(hotValue);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}