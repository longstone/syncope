/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.syncope.fit.buildtools.cxf;

import javax.ws.rs.core.Context;
import org.apache.cxf.jaxrs.ext.MessageContext;
import com.icegreen.greenmail.smtp.InterruptableGreenMail;
import org.apache.syncope.fit.buildtools.GreenMailStartStopListener;
import com.icegreen.greenmail.smtp.InterruptableSmtpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GreenMailServiceImpl implements GreenMailService {

    private static final Logger LOG = LoggerFactory.getLogger(GreenMailService.class);

    @Context
    private MessageContext messageContext;

    @Override
    public void start() {
        InterruptableGreenMail greenMail = (InterruptableGreenMail) messageContext.getServletContext().
                getAttribute(GreenMailStartStopListener.GREENMAIL);
        if (greenMail != null) {
            ((InterruptableSmtpServer) greenMail.getSmtp()).setRejectRequests(false);
            LOG.info("SMTP server is accepting requests");
        }
    }

    @Override
    public void stop() {
        InterruptableGreenMail greenMail = (InterruptableGreenMail) messageContext.getServletContext().
                getAttribute(GreenMailStartStopListener.GREENMAIL);
        if (greenMail != null) {
            ((InterruptableSmtpServer) greenMail.getSmtp()).setRejectRequests(true);
            LOG.info("SMTP server is rejecting requests");
        }
    }
}
