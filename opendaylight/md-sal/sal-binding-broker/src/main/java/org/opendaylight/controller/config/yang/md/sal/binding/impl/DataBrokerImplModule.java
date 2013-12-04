/**
 * Generated file

 * Generated from: yang module name: opendaylight-sal-binding-broker-impl  yang module local name: binding-data-broker
 * Generated by: org.opendaylight.controller.config.yangjmxgenerator.plugin.JMXGenerator
 * Generated at: Wed Nov 20 17:33:01 CET 2013
 *
 * Do not modify this file unless it is present under src/main directory
 */
package org.opendaylight.controller.config.yang.md.sal.binding.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.opendaylight.controller.config.yang.md.sal.binding.statistics.DataBrokerRuntimeMXBeanImpl;
import org.opendaylight.controller.md.sal.common.impl.routing.AbstractDataReadRouter;
import org.opendaylight.controller.sal.binding.impl.DataBrokerImpl;
import org.opendaylight.controller.sal.binding.impl.connect.dom.BindingIndependentDataServiceConnector;
import org.opendaylight.controller.sal.binding.impl.connect.dom.BindingIndependentMappingService;
import org.opendaylight.controller.sal.core.api.Broker;
import org.opendaylight.controller.sal.core.api.data.DataProviderService;
import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.osgi.framework.BundleContext;

import com.google.common.util.concurrent.MoreExecutors;

/**
*
*/
public final class DataBrokerImplModule extends
        org.opendaylight.controller.config.yang.md.sal.binding.impl.AbstractDataBrokerImplModule {

    private BundleContext bundleContext;

    public DataBrokerImplModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier,
            org.opendaylight.controller.config.api.DependencyResolver dependencyResolver) {
        super(identifier, dependencyResolver);
    }

    public DataBrokerImplModule(org.opendaylight.controller.config.api.ModuleIdentifier identifier,
            org.opendaylight.controller.config.api.DependencyResolver dependencyResolver,
            DataBrokerImplModule oldModule, java.lang.AutoCloseable oldInstance) {
        super(identifier, dependencyResolver, oldModule, oldInstance);
    }

    @Override
    public void validate() {
        super.validate();
    }

    @Override
    public java.lang.AutoCloseable createInstance() {
        DataBrokerRuntimeMXBeanImpl dataBindingBroker = new DataBrokerRuntimeMXBeanImpl();
        
        // FIXME: obtain via dependency management
        ExecutorService executor = Executors.newCachedThreadPool();
        ExecutorService listeningExecutor = MoreExecutors.listeningDecorator(executor);
        dataBindingBroker.setExecutor(listeningExecutor);

        Broker domBroker = getDomBrokerDependency();
        BindingIndependentMappingService mappingService = getMappingServiceDependency();
        
        if (domBroker != null && mappingService != null) {
            BindingIndependentDataServiceConnector runtimeMapping = new BindingIndependentDataServiceConnector();
            runtimeMapping.setMappingService(mappingService);
            runtimeMapping.setBaDataService(dataBindingBroker);
            domBroker.registerProvider(runtimeMapping, getBundleContext());
        }
        getRootRuntimeBeanRegistratorWrapper().register(dataBindingBroker);
        return dataBindingBroker;
    }

    public BundleContext getBundleContext() {
        return bundleContext;
    }

    public void setBundleContext(BundleContext bundleContext2) {
        this.bundleContext = bundleContext2;
    }
}