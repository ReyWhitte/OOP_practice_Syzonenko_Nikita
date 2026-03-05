package ex01;

import java.io.Serializable;

/**
 * Клас для зберігання вхідних даних та результатів.
 */
public class Item2d implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** Номер телефону - transient для демонстрації */
    private transient String phoneNumber;
    
    /** Очищений номер (10 цифр, напр. 0671234567) */
    private String cleanNumber;
    
    /** Код оператора (без 0, напр. "67") */
    private String operatorCode;
    
    /** Назва оператора */
    private String operatorName;
    
    /** Форматований номер */
    private String formattedNumber;
    
    /** Чи валідний номер */
    private boolean valid;
    
    public Item2d() {
        this.phoneNumber = "";
        this.cleanNumber = "";
        this.operatorCode = "";
        this.operatorName = MobileOperator.UNKNOWN.getName();
        this.formattedNumber = "";
        this.valid = false;
    }
    
    public String setPhoneNumber(String phoneNumber) {
        return this.phoneNumber = phoneNumber;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public String setCleanNumber(String cleanNumber) {
        return this.cleanNumber = cleanNumber;
    }
    
    public String getCleanNumber() {
        return cleanNumber;
    }
    
    public String setOperatorCode(String operatorCode) {
        return this.operatorCode = operatorCode;
    }
    
    public String getOperatorCode() {
        return operatorCode;
    }
    
    public String setOperatorName(String operatorName) {
        return this.operatorName = operatorName;
    }
    
    public String getOperatorName() {
        return operatorName;
    }
    
    public String setFormattedNumber(String formattedNumber) {
        return this.formattedNumber = formattedNumber;
    }
    
    public String getFormattedNumber() {
        return formattedNumber;
    }
    
    public boolean setValid(boolean valid) {
        return this.valid = valid;
    }
    
    public boolean isValid() {
        return valid;
    }
    
    // Для сумісності з старими тестами
    public boolean getIsValid() {
        return valid;
    }
    
    @Override
    public String toString() {
        if (!valid) {
            return String.format("Номер: %s (невалідний)", phoneNumber);
        }
        return String.format("Номер: %s | Очищений: %s | Код: %s | Оператор: %s | Формат: %s", 
                            phoneNumber, cleanNumber, operatorCode, operatorName, formattedNumber);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        Item2d other = (Item2d) obj;
        
        if (cleanNumber == null) {
            if (other.cleanNumber != null) return false;
        } else if (!cleanNumber.equals(other.cleanNumber)) return false;
        
        if (operatorCode == null) {
            if (other.operatorCode != null) return false;
        } else if (!operatorCode.equals(other.operatorCode)) return false;
        
        if (operatorName == null) {
            if (other.operatorName != null) return false;
        } else if (!operatorName.equals(other.operatorName)) return false;
        
        return true;
    }
}