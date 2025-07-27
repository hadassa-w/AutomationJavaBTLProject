package enums;

public enum MainMenu {
    M_Z("מיצוי זכויות"),
    K_H("קצבאות והטבות"),
    D_B("דמי ביטוח"),
    Y_K("יצירת קשר"),
    S("סניפים"),
    T("תשלומים"),
    S_I("שירות אישי");

    private final String menuText;

    MainMenu(String menuText) {
        this.menuText = menuText;
    }

    public String getMenuText() {
        return menuText;
    }
}
