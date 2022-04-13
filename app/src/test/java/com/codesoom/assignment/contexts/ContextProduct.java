package com.codesoom.assignment.contexts;

import com.codesoom.assignment.domains.Product;
import com.codesoom.assignment.enums.Category;

public abstract class ContextProduct {

    protected Product generateFirstProduct() {
        return new Product(Category.TOY, "캣타워", "캣러버스", 5000, "https://cdn.imweb.me/thumbnail/20200825/b940aaa4583a4.jpg");
    }

    protected Product generateSecondProduct() {
        return new Product(Category.TOY, "고양이 낚시대", "캣러버스", 10000, "http://m.wooayeou.com/web/product/big/201706/269_shop1_759376.jpg");
    }

}
