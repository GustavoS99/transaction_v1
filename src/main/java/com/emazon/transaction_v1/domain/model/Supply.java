package com.emazon.transaction_v1.domain.model;

import com.emazon.transaction_v1.domain.model.builder.IBuilder;

import java.time.Duration;
import java.time.LocalDateTime;

import static com.emazon.transaction_v1.util.GlobalConstants.DAYS_UNTIL_NEXT_SUPPLY;

public class Supply {

    private Long id;

    private Long itemId;

    private Long userId;

    private Long quantity;

    private LocalDateTime addedAt;

    private LocalDateTime nextSupplyAt;

    public static SupplyBuilder builder() {
        return new SupplyBuilder();
    }

    public static class SupplyBuilder implements IBuilder<Supply> {

        private Long id;

        private Long itemId;

        private Long userId;

        private Long quantity;

        private LocalDateTime addedAt;

        private LocalDateTime nextSupplyAt;

        public SupplyBuilder() {
            addedAt = LocalDateTime.now();
            nextSupplyAt = addedAt.plus(Duration.ofDays(DAYS_UNTIL_NEXT_SUPPLY));
        }

        public SupplyBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SupplyBuilder itemId(Long itemId) {
            this.itemId = itemId;
            return this;
        }

        public SupplyBuilder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public SupplyBuilder quantity(Long quantity) {
            this.quantity = quantity;
            return this;
        }

        public SupplyBuilder addedAt(LocalDateTime addedAt) {
            this.addedAt = addedAt;
            return this;
        }

        public SupplyBuilder nextSupplyAt(LocalDateTime nextSupplyAt) {
            this.nextSupplyAt = nextSupplyAt;
            return this;
        }

        @Override
        public Supply build() {
            Supply supply = new Supply();

            supply.setId(id);

            supply.setItemId(itemId);

            supply.setUserId(userId);

            supply.setQuantity(quantity);

            supply.setAddedAt(addedAt);

            supply.setNextSupplyAt(nextSupplyAt);

            return supply;
        }
    }

    public Supply() {
        addedAt = LocalDateTime.now();
        nextSupplyAt = addedAt.plus(Duration.ofDays(DAYS_UNTIL_NEXT_SUPPLY));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public LocalDateTime getNextSupplyAt() {
        return nextSupplyAt;
    }

    public void setNextSupplyAt(LocalDateTime nextSupplyAt) {
        this.nextSupplyAt = nextSupplyAt;
    }
}
