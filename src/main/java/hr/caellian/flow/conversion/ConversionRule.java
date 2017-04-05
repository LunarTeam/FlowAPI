package hr.caellian.flow.conversion;

import hr.caellian.flow.data.FluxType;

import java.util.ArrayList;

/**
 * Convert Rule allows filtering which conversions are allowed and which are not.
 *
 * @author Caellian
 * @since 1.0.0
 */
public class ConversionRule {

    /**
     * {@link hr.caellian.flow.data.FluxType Flux Type} to which this
     * {@link ConversionRule Conversion Rule} belongs.
     */
    private final FluxType owner;

    /**
     * Filtering mode to use for conversion permission selection.
     */
    private FilterMode filterMode;

    /**
     * List containing {@link hr.caellian.flow.data.FluxType Flux Types}
     * {@link #owner owner Flux Type} can be converted from.
     */
    private ArrayList<FluxType> from = new ArrayList<>();

    /**
     * List containing {@link hr.caellian.flow.data.FluxType Flux Types}
     * {@link #owner owner Flux Type} can be converted into.
     */
    private ArrayList<FluxType> to = new ArrayList<>();

    /**
     * Default blacklist constructor.
     *
     * @param owner owner of this conversion management rule.
     */
    public ConversionRule(FluxType owner) {
        this(owner, FilterMode.BLACKLIST);
    }

    /**
     * Default blacklist constructor.
     *
     * @param owner owner of this conversion management rule.
     * @param filterMode mode to filter entries with.
     */
    public ConversionRule(FluxType owner, FilterMode filterMode) {
        this.owner = owner;
        this.filterMode = filterMode;
    }

    /**
     * This method tests if given {@link FluxType Flux Type} is allowed to be
     * converted to {@link #owner} {@link FluxType Flux Type} or converted from
     * it.
     *
     * @param convertDirection {@code FROM} represents conversion happening from
     * {@code other} {@link FluxType Flux Type} to
     * {@link #owner owner Flux Type} and {@code INTO} the opposite.
     * @param other type of {@code other} {@link FluxType Flux Type}.
     * @return {@code true} if conversion is allowed by this conversion rule,
     * {@code false} otherwise.
     */
    public boolean test(ConvertDirection convertDirection, FluxType other) {
        if ((from.contains(other) && convertDirection == ConvertDirection.FROM)
            || (to.contains(other) && convertDirection == ConvertDirection.INTO)){
            return !filterMode.getDefaultResponse();
        }
        return filterMode.getDefaultResponse();
    }

    /**
     * @param fluxType Flux Type to add to {@link #from "from"} exception list.
     * @return {@code true} if {@link #from "from"} exception was added successfully.
     */
    public boolean addFromException(FluxType fluxType) {
        return from.add(fluxType);
    }

    /**
     * @param fluxType Flux Type to remove from {@link #from "from"} exception list.
     * @return {@code true} if {@link #from "from"} exception was removed successfully.
     */
    public boolean removeFromException(FluxType fluxType) {
        return from.remove(fluxType);
    }

    /**
     * @param fluxType Flux Type to add to filtering mode this conversion rule is using. exception list.
     * @return {@code true} if {@link #to "to"} exception was added successfully.
     */
    public boolean addToException(FluxType fluxType) {
        return to.add(fluxType);
    }

    /**
     * @param fluxType Flux Type to remove from {@link #to "to"} exception list.
     * @return {@code true} if {@link #to "to"} exception was removed successfully.
     */
    public boolean removeToException(FluxType fluxType) {
        return to.remove(fluxType);
    }

    /**
     * @return filtering mode this conversion rule is using.
     */
    public FilterMode getFilterMode() {
        return filterMode;
    }

    /**
     * @param runMode filtering mode this conversion rule should use.
     */
    public void setFilterMode(FilterMode runMode) {
        this.filterMode = runMode;
    }

    /**
     * {@link FilterMode Filter Mode} controls the way entries in {@link #from "from"}
     * and {@link #to "to"} lists are filtered. {@link FilterMode Filter Modes}
     * make it a lot easier to efficiently filter data.
     */
    public enum FilterMode {
        /**
         * Blacklist mode allows all conversions except the ones listed in
         * {@link #from "from"} and {@link #to "to"} lists.
         */
        BLACKLIST(true),
        /**
         * Whitelist mode allows all conversions listed in {@link #from "from"}
         * and {@link #to "to"} lists, but prevents all other.
         */
        WHITELIST(false);

        /**
         * Default response is response given when the acquired item is not found
         * on queried list.
         */
        private final boolean defaultResponse;

        /**
         * Default constructor for {@link FilterMode Filter Mode}.
         * @param defaultResponse response given when the acquired item is not
         *                        found on queried list.
         */
        FilterMode(boolean defaultResponse) {
            this.defaultResponse = defaultResponse;
        }

        /**
         * @return response given when the acquired item is not found on queried
         * list.
         */
        public boolean getDefaultResponse() {
            return defaultResponse;
        }
    }

    /**
     * {@link ConvertDirection Convert Direction} handles the direction of
     * queried conversion.
     */
    public enum ConvertDirection {
        /**
         * Into represents conversion happening from {@link #owner owner Flux Type}
         * to other {@link FluxType Flux Type}.
         */
        INTO,
        /**
         * Into represents conversion happening from other {@link FluxType Flux Type}
         * to {@link #owner owner Flux Type}.
         */
        FROM
    }
}