package com.emazon.transaction_v1.domain.model;

import com.emazon.transaction_v1.domain.model.builder.IBuilder;

public class ItemUpdateQuantity {

    private Long id;

    private Long quantity;

    public static ItemUpdateQuantityBuilder builder() {
        return new ItemUpdateQuantityBuilder();
    }

    public static class ItemUpdateQuantityBuilder implements IBuilder<ItemUpdateQuantity> {

        private Long id;

        private Long quantity;

        public ItemUpdateQuantityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public ItemUpdateQuantityBuilder quantity(Long quantity) {
            this.quantity = quantity;
            return this;
        }

        @Override
        public ItemUpdateQuantity build() {
            ItemUpdateQuantity itemUpdateQuantity = new ItemUpdateQuantity();

            itemUpdateQuantity.setId(id);

            itemUpdateQuantity.setQuantity(quantity);

            return itemUpdateQuantity;
        }
    }

    ItemUpdateQuantity() {}

    ItemUpdateQuantity(Long id, Long quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
