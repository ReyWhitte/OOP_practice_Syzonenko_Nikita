package ex03;

import java.io.Serializable;

/**
 * Клас для зберігання інформації про номер телефону.
 * Використовується як елемент колекції.
 * 
 * @author Сизоненко Нікіта
 * @version 2.0
 */
public class PhoneEntry implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** Оригінальний номер телефону */
    private String originalNumber;
    
    /** Очищений номер (10 цифр) */
    private String cleanNumber;
    
    /** Код оператора */
    private String operatorCode;
    
    /** Назва оператора */
    private String operatorName;
    
    /**
     * Конструктор за замовчуванням.
     */
    public PhoneEntry() {
        this.originalNumber = "";
        this.cleanNumber = "";
        this.operatorCode = "";
        this.operatorName = MobileOperator.UNKNOWN.getName();
    }
    
    /**
     * Конструктор з параметрами.
     * @param originalNumber оригінальний номер
     * @param cleanNumber очищений номер
     * @param operatorCode код оператора
     * @param operatorName назва оператора
     */
    public PhoneEntry(String originalNumber, String cleanNumber, 
                      String operatorCode, String operatorName) {
        this.originalNumber = originalNumber;
        this.cleanNumber = cleanNumber;
        this.operatorCode = operatorCode;
        this.operatorName = operatorName;
    }
    
    // Геттери та сеттери
    public String getOriginalNumber() { return originalNumber; }
    public void setOriginalNumber(String originalNumber) { this.originalNumber = originalNumber; }
    
    public String getCleanNumber() { return cleanNumber; }
    public void setCleanNumber(String cleanNumber) { this.cleanNumber = cleanNumber; }
    
    public String getOperatorCode() { return operatorCode; }
    public void setOperatorCode(String operatorCode) { this.operatorCode = operatorCode; }
    
    public String getOperatorName() { return operatorName; }
    public void setOperatorName(String operatorName) { this.operatorName = operatorName; }
    
    /**
     * Форматує номер для відображення.
     * @return форматований номер
     */
    public String getFormattedNumber() {
        if (cleanNumber == null || cleanNumber.length() != 10 || !cleanNumber.startsWith("0")) {
            return originalNumber;
        }
        
        String code = cleanNumber.substring(1, 3);
        String part1 = cleanNumber.substring(3, 6);
        String part2 = cleanNumber.substring(6, 8);
        String part3 = cleanNumber.substring(8, 10);
        
        return String.format("+380 (%s) %s-%s-%s", code, part1, part2, part3);
    }
    
    @Override
    public String toString() {
        return String.format("%s -> %s (код: %s)", 
                            originalNumber, operatorName, operatorCode);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        
        PhoneEntry other = (PhoneEntry) obj;
        
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
    
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (cleanNumber != null ? cleanNumber.hashCode() : 0);
        result = 31 * result + (operatorCode != null ? operatorCode.hashCode() : 0);
        result = 31 * result + (operatorName != null ? operatorName.hashCode() : 0);
        return result;
    }
}