Set up service account:

> kubectl create -f src/main/kube/camel-batch-sa.yaml

> oadm policy add-role-to-user cluster-admin system:serviceaccount:NAMESPACE:camel-batch

(should we use `admin` vs `cluster-admin` ? probably..)

see https://docs.openshift.com/enterprise/3.1/admin_guide/manage_authorization_policy.html