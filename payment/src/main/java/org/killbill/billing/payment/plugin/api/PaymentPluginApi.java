/*
 * Copyright 2010-2013 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package org.killbill.billing.payment.plugin.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.killbill.billing.catalog.api.Currency;
import org.killbill.billing.payment.api.PaymentMethodPlugin;
import org.killbill.billing.util.callcontext.CallContext;
import org.killbill.billing.util.callcontext.TenantContext;
import org.killbill.billing.util.entity.Pagination;

public interface PaymentPluginApi {

    /**
     * Authorize a specific amount in the Gateway.
     *
     * @param kbAccountId       killbill accountId
     * @param kbPaymentId       killbill payment id (for reference)
     * @param kbPaymentMethodId killbill payment method id
     * @param amount            amount to charge
     * @param currency          currency
     * @param context           call context
     * @return information about the authorization in the gateway
     * @throws PaymentPluginApiException
     */
    public PaymentInfoPlugin authorizePayment(UUID kbAccountId, UUID kbPaymentId, UUID kbPaymentMethodId, BigDecimal amount, Currency currency, CallContext context)
            throws PaymentPluginApiException;

    /**
     * Capture a specific amount in the Gateway.
     *
     * @param kbAccountId       killbill accountId
     * @param kbPaymentId       killbill payment id (for reference)
     * @param kbPaymentMethodId killbill payment method id
     * @param amount            amount to charge
     * @param currency          currency
     * @param context           call context
     * @return information about the capture in the gateway
     * @throws PaymentPluginApiException
     */
    public PaymentInfoPlugin capturePayment(UUID kbAccountId, UUID kbPaymentId, UUID kbPaymentMethodId, BigDecimal amount, Currency currency, CallContext context)
            throws PaymentPluginApiException;

    /**
     * Charge a specific amount in the Gateway. Required.
     *
     * @param kbAccountId       killbill accountId
     * @param kbPaymentId       killbill payment id (for reference)
     * @param kbPaymentMethodId killbill payment method id
     * @param amount            amount to charge
     * @param currency          currency
     * @param context           call context
     * @return information about the payment in the gateway
     * @throws PaymentPluginApiException
     */
    public PaymentInfoPlugin processPayment(UUID kbAccountId, UUID kbPaymentId, UUID kbPaymentMethodId, BigDecimal amount, Currency currency, CallContext context)
            throws PaymentPluginApiException;

    /**
     * Void an authorization in the Gateway.
     *
     * @param kbAccountId       killbill accountId
     * @param kbPaymentId       killbill payment id (for reference)
     * @param kbPaymentMethodId killbill payment method id
     * @param context           call context
     * @return information about the capture in the gateway
     * @throws PaymentPluginApiException
     */
    public PaymentInfoPlugin voidPayment(UUID kbAccountId, UUID kbPaymentId, UUID kbPaymentMethodId, CallContext context)
            throws PaymentPluginApiException;

    /**
     * Retrieve information about a given payment. Optional (not all gateways will support it).
     *
     * @param kbAccountId killbill accountId
     * @param kbPaymentId killbill payment id (for reference)
     * @param context     call context
     * @return information about the payment in the gateway
     * @throws PaymentPluginApiException
     */
    public PaymentInfoPlugin getPaymentInfo(UUID kbAccountId, UUID kbPaymentId, TenantContext context)
            throws PaymentPluginApiException;

    /**
     * Search payments
     * <p/>
     * The search is plugin specific, there is no constraint on how the searchKey should be interpreted.
     *
     * @param context call context
     * @param offset  the offset of the first result
     * @param limit   the maximum number of results to retrieve
     * @return payments matching the search key
     */
    public Pagination<PaymentInfoPlugin> searchPayments(String searchKey, Long offset, Long limit, TenantContext context) throws PaymentPluginApiException;

    /**
     * Process a refund against a given payment. Required.
     *
     * @param kbAccountId  killbill accountId
     * @param kbPaymentId  killbill payment id (for reference)
     * @param refundAmount refund amount
     * @param currency     currency
     * @param context      call context
     * @return information about the refund in the gateway
     * @throws PaymentPluginApiException
     */
    public RefundInfoPlugin processRefund(UUID kbAccountId, UUID kbPaymentId, BigDecimal refundAmount, Currency currency, CallContext context)
            throws PaymentPluginApiException;

    /**
     * @param kbAccountId killbill account id
     * @param kbPaymentId killbill payment id
     * @param context     call context
     * @return information about the refunds in the gateway
     * @throws PaymentPluginApiException
     */
    public List<RefundInfoPlugin> getRefundInfo(UUID kbAccountId, UUID kbPaymentId, TenantContext context)
            throws PaymentPluginApiException;

    /**
     * Search refunds
     * <p/>
     * The search is plugin specific, there is no constraint on how the searchKey should be interpreted.
     *
     * @param context call context
     * @param offset  the offset of the first result
     * @param limit   the maximum number of results to retrieve
     * @return refunds matching the search key
     */
    public Pagination<RefundInfoPlugin> searchRefunds(String searchKey, Long offset, Long limit, TenantContext context) throws PaymentPluginApiException;

    /**
     * Add a payment method for a Killbill account in the gateway. Optional.
     * <p/>
     * Note: the payment method doesn't exist yet in Killbill when receiving the call in
     * the plugin (kbPaymentMethodId is a placeholder).
     *
     * @param kbAccountId        killbill accountId
     * @param paymentMethodProps payment method details
     * @param setDefault         set it as the default payment method in the gateway
     * @param context            call context
     * @throws PaymentPluginApiException
     */
    public void addPaymentMethod(UUID kbAccountId, UUID kbPaymentMethodId, PaymentMethodPlugin paymentMethodProps, boolean setDefault, CallContext context)
            throws PaymentPluginApiException;

    /**
     * Delete a payment method in the gateway. Optional.
     *
     * @param kbAccountId       killbill accountId
     * @param kbPaymentMethodId killbill payment method id
     * @param context           call context
     * @throws PaymentPluginApiException
     */
    public void deletePaymentMethod(UUID kbAccountId, UUID kbPaymentMethodId, CallContext context)
            throws PaymentPluginApiException;

    /**
     * Get payment method details for a given payment method. Optional.
     *
     * @param kbAccountId       killbill account id
     * @param kbPaymentMethodId killbill payment method id.
     * @param context           call context
     * @return PaymentMethodPlugin info for the payment method
     * @throws PaymentPluginApiException
     */
    public PaymentMethodPlugin getPaymentMethodDetail(UUID kbAccountId, UUID kbPaymentMethodId, TenantContext context)
            throws PaymentPluginApiException;

    /**
     * Set a payment method as default in the gateway. Optional.
     *
     * @param kbAccountId       killbill accountId
     * @param kbPaymentMethodId killbill payment method id
     * @param context           call context
     * @throws PaymentPluginApiException
     */
    public void setDefaultPaymentMethod(UUID kbAccountId, UUID kbPaymentMethodId, CallContext context)
            throws PaymentPluginApiException;

    /**
     * This is used to see the view of paymentMethods kept by the plugin or the view of
     * existing payment method on the gateway.
     * <p/>
     * Sometimes payment methods have to be added directly to the gateway for PCI compliance issues
     * and so Killbill needs to refresh its state.
     *
     * @param kbAccountId        killbill accountId
     * @param refreshFromGateway fetch the list of existing  payment methods from gateway-- if supported
     * @param context            call context
     * @return
     */
    public List<PaymentMethodInfoPlugin> getPaymentMethods(UUID kbAccountId, boolean refreshFromGateway, CallContext context)
            throws PaymentPluginApiException;

    /**
     * Search payment methods
     * <p/>
     * The search is plugin specific, there is no constraint on how the searchKey should be interpreted.
     *
     * @param context call context
     * @param offset  the offset of the first result
     * @param limit   the maximum number of results to retrieve
     * @return payment methods matching the search key
     */
    public Pagination<PaymentMethodPlugin> searchPaymentMethods(String searchKey, Long offset, Long limit, TenantContext context)
            throws PaymentPluginApiException;

    /**
     * This is used after Killbill decided to refresh its state from the gateway
     *
     * @param kbAccountId    killbill accountId
     * @param paymentMethods the list of payment methods
     */
    public void resetPaymentMethods(UUID kbAccountId, List<PaymentMethodInfoPlugin> paymentMethods)
            throws PaymentPluginApiException;

    /**
     * Build metadata for the client to create a redirect form
     *
     * @param kbAccountId      killbill accountId
     * @param descriptorFields form fields
     * @param context          call context
     * @return redirect form metadata
     */
    public HostedPaymentPageFormDescriptor buildFormDescriptor(UUID kbAccountId, HostedPaymentPageDescriptorFields descriptorFields, TenantContext context);

    /**
     * Process a notification from the gateway
     * <p/>
     * This potentially does more than just deserialize the payload. The plugin may have to acknowledge it
     * with the gateway.
     *
     * @param notification serialized notification object
     * @param context      call context
     * @return deserialized notification object
     * @throws PaymentPluginApiException
     */
    public HostedPaymentPageNotification processNotification(String notification, TenantContext context)
            throws PaymentPluginApiException;
}
