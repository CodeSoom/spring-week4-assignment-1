package com.codesoom.assignment.product;

import com.codesoom.assignment.product.domain.Product;

public class ProductFixtures {
    public static Product laser() {
        return new Product(
                1L,
                "kubernetes",
                "google",
                1_999_999L,
                "https://d33wubrfki0l68.cloudfront.net/688a7bc98d1e09112a54286a9282073c7bf5ee78/5cff4/images/blog/2020-12-08-kubernetes-1.20-release-announcement/laser.png");
    }
    public static Product helm() {
        return new Product(
                2L,
                "calico",
                "tigera",
                333_000L,
                "https://www.projectcalico.org/wp-content/uploads/2020/08/Calico-Networking-For-Kubernetes.png");
    }
}
