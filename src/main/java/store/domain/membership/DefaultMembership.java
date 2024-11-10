package store.domain.membership;

public class DefaultMembership implements Membership {

    private static final double MEMBERSHIP_DISCOUNT = 0.3;
    private static final int MAXIMUM_MEMBERSHIP_DISCOUNT = 8000;


    @Override
    public int calculateDiscount(int input) {
        int discount = (int) (input * MEMBERSHIP_DISCOUNT);
        if (discount > MAXIMUM_MEMBERSHIP_DISCOUNT) {
            return MAXIMUM_MEMBERSHIP_DISCOUNT;
        }
        return discount;
    }
}
