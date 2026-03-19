package ex03;

/**
 * Перелік мобільних операторів України.
 */
public enum MobileOperator {
    KYIVSTAR("Київстар", new String[]{"67", "68", "96", "97", "98"}),
    VODAFONE("Vodafone", new String[]{"50", "66", "95", "99"}),
    LIFECELL("lifecell", new String[]{"63", "73", "93"}),
    THREE_MOB("3Mob", new String[]{"91"}),
    INTERTELECOM("Інтертелеком", new String[]{"89", "94"}),
    PEOPLENET("Peoplenet", new String[]{"92"}),
    UNKNOWN("Невідомий оператор", new String[]{});
    
    private final String name;
    private final String[] codes;
    
    MobileOperator(String name, String[] codes) {
        this.name = name;
        this.codes = codes;
    }
    
    public String getName() {
        return name;
    }
    
    public String[] getCodes() {
        return codes.clone();
    }
    
    /**
     * Визначає оператора за кодом.
     * @param operatorCode код оператора (наприклад, "67")
     * @return відповідний оператор
     */
    public static MobileOperator detectByCode(String operatorCode) {
        if (operatorCode == null || operatorCode.isEmpty()) {
            return UNKNOWN;
        }
        
        String cleanCode = operatorCode.startsWith("0") ? operatorCode.substring(1) : operatorCode;
        
        for (MobileOperator operator : values()) {
            for (String code : operator.codes) {
                if (cleanCode.equals(code)) {
                    return operator;
                }
            }
        }
        
        return UNKNOWN;
    }
    
    @Override
    public String toString() {
        return name;
    }
}