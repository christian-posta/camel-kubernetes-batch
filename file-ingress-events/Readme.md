mvn clean install docker:build fabric8:apply

on host, create:

/opt/camel/incoming
/opt/camel/outgoing


note need to relax scc restricted to allow host volumes (for demos):

[root@localhost ~]# oc get scc restricted -o yaml
allowHostDirVolumePlugin: true
allowHostIPC: false
allowHostNetwork: false
allowHostPID: false
allowHostPorts: false
allowPrivilegedContainer: false
allowedCapabilities: null
apiVersion: v1
fsGroup:
  type: RunAsAny
groups:
- system:authenticated
kind: SecurityContextConstraints
metadata:
  annotations:
    kubernetes.io/description: restricted denies access to all host features and requires
      pods to be run with a UID, and SELinux context that are allocated to the namespace.  This
      is the most restrictive SCC.
  creationTimestamp: 2016-02-24T23:14:40Z
  name: restricted
  resourceVersion: "9829"
  selfLink: /api/v1/securitycontextconstraints/restricted
  uid: 61999819-db4c-11e5-b715-525400d0cdc3
priority: null
runAsUser:
  type: RunAsAny
seLinuxContext:
  type: RunAsAny
supplementalGroups:
  type: RunAsAny