package fon.master.nst.orchestrationservice.dto;

public class ProductCategory {

    private Long categoryId;
    private String name;
    private String categoryImgPath;

    public ProductCategory() {

    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryImgPath() {
        return categoryImgPath;
    }

    public void setCategoryImgPath(String categoryImgPath) {
        this.categoryImgPath = categoryImgPath;
    }

    @Override
    public boolean equals(Object obj) {
        ProductCategory productCategory = (ProductCategory) obj;
        if (categoryId == productCategory.getCategoryId()) {
            return true;
        }
        return false;
    }

}