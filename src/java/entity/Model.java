package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author makso
 */
@Entity
@XmlRootElement
public class Model implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String modelName;
    private String modelSize;
    private int amount;
    private long price;
    private String modelFirm;

    @Override
    public String toString() {
        return "Model{" + "id=" + id + ", modelName=" + modelName + ", modelSize=" + modelSize + ", amount=" + amount + ", price=" + price + ", modelFirm=" + modelFirm + '}';
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.id);
        hash = 11 * hash + Objects.hashCode(this.modelName);
        hash = 11 * hash + Objects.hashCode(this.modelSize);
        hash = 11 * hash + this.amount;
        hash = 11 * hash + (int) (this.price ^ (this.price >>> 32));
        hash = 11 * hash + Objects.hashCode(this.modelFirm);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Model other = (Model) obj;
        if (this.amount != other.amount) {
            return false;
        }
        if (this.price != other.price) {
            return false;
        }
        if (!Objects.equals(this.modelName, other.modelName)) {
            return false;
        }
        if (!Objects.equals(this.modelSize, other.modelSize)) {
            return false;
        }
        if (!Objects.equals(this.modelFirm, other.modelFirm)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelSize() {
        return modelSize;
    }

    public void setModelSize(String modelSize) {
        this.modelSize = modelSize;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getModelFirm() {
        return modelFirm;
    }

    public void setModelFirm(String modelFirm) {
        this.modelFirm = modelFirm;
    }
    
    
}