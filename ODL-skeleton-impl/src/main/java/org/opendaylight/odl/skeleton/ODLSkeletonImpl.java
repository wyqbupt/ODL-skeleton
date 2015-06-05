package org.opendaylight.odl.skeleton;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.ProviderContext;
import org.opendaylight.controller.sal.binding.api.BindingAwareProvider;

// Interfaces generated from the skeleton yang model
import org.opendaylight.yang.gen.v1.http.netconfcentral.org.ns.skeleton.rev150524.Skeleton;
import org.opendaylight.yang.gen.v1.http.netconfcentral.org.ns.skeleton.rev150524.SkeletonBuilder;

import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ODLSkeletonImpl implements BindingAwareProvider, AutoCloseable {
    private static final Logger LOG = LoggerFactory.getLogger(ODLSkeletonImpl.class);

    private ProviderContext providerContext;
    private DataBroker dataService;

    public static final InstanceIdentifier<Skeleton> Skeleton_ID = InstanceIdentifier.builder(Skeleton.class).build();
    private static final java.lang.String NAME = new java.lang.String("TCP");
    private static final java.lang.String PROTOCOL = new java.lang.String("The Transmission Control Protocol (TCP) is a core protocol of the Internet Protocol Suite. It originated in the initial network implementation in which it complemented the Internet Protocol (IP). Therefore, the entire suite is commonly referred to as TCP/IP. TCP provides reliable, ordered, and error-checked delivery of a stream of octets between applications running on hosts communicating over an IP network. TCP is the protocol that major Internet applications such as the World Wide Web, email, remote administration and file transfer rely on. Applications that do not require reliable data stream service may use the User Datagram Protocol (UDP), which provides a connectionless datagram service that emphasizes reduced latency over reliability.");
    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
    }
    @Override
    public void onSessionInitiated(ProviderContext session) {
        this.providerContext = session;
        this.dataService = session.getSALService(DataBroker.class);

        // Initialize operational and default config data in MD-SAL data store
        initOperational();
        initConfiguration();

        LOG.info("onSessionInitiated: initialization done");
        LOG.info("ODLSkeletonImpl initialized!");
    }

    /**************************************************************************
     * ODLSkeletonImpl Private Methods
     *************************************************************************/

    /**
     * Populates ODLSkeleton's initial operational data into the MD-SAL operational
     * data store.
     * Note - we are simulating protocol writing This is why the name and protocol
     * name are hardcoded.
     */
    private void initOperational() {
        // Build the initial test protocol operational data
        Skeleton protocol = new SkeletonBuilder()
                .setName( NAME )
                .setDescription( PROTOCOL )
                .build();

        // Put the protocol operational data into the MD-SAL data store
        WriteTransaction tx = dataService.newWriteOnlyTransaction();
        tx.put(LogicalDatastoreType.OPERATIONAL, Skeleton_ID, protocol);

        // Perform the tx.submit asynchronously
        Futures.addCallback(tx.submit(), new FutureCallback<Void>() {
            @Override
            public void onSuccess(final Void result) {
                LOG.info("initOperational: transaction succeeded");
            }

            @Override
            public void onFailure(final Throwable t) {
                LOG.error("initOperational: transaction failed");
            }
        });

        LOG.info("initOperational: operational status populated: {}", protocol);
    }

    /**
     * Populates ODLSkeleton's default config data into the MD-SAL configuration
     * data store.  Note the database write to the tree are done in a synchronous fashion
     */
    private void initConfiguration() {
        // Build the default ODLSkeleton config data
        Skeleton protocol = new SkeletonBuilder().setNum((long)1 ).build();

        // Place default config data in data store tree
        WriteTransaction tx = dataService.newWriteOnlyTransaction();
        tx.put(LogicalDatastoreType.CONFIGURATION, Skeleton_ID, protocol);
        // Perform the tx.submit synchronously
        tx.submit();

        LOG.info("initOperational: default config populated: {}", protocol);
    }


}
