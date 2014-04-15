/*
 * Copyright 2014 The Billing Project, LLC
 *
 * The Billing Project licenses this file to you under the Apache License, version 2.0
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

import java.util.Map;

import org.killbill.billing.catalog.api.Currency;

public interface HostedPaymentPageDescriptorFields {

    public String getAmount();

    public Currency currency();

    public String getOrder();

    public String getCredential2();

    public String getCredential3();

    public String getCredential4();

    public String getTransactionType();

    public String getAuthCode();

    public String getAccountName();

    public interface Customer {

        public String getFirstName();

        public String getLastName();

        public String getEmail();

        public String getPhone();
    }

    public interface BillingAddress {

        public String getCity();

        public String getAddress1();

        public String getAddress2();

        public String getState();

        public String getZip();

        public String getCountry();
    }

    public String getNotifyUrl();

    public String getReturnUrl();

    public String getForwardUrl();

    public String getCancelReturnUrl();

    public String getRedirectParam();

    public String getDescription();

    public String getTax();

    public String getShipping();

    public Map<String, String> customFields();
}
