resource "aws_eks_access_entry" "eks-access_entry" {
  cluster_name      = aws_eks_cluster.eks-cluster.name
  principal_arn     = var.principalArn
  kubernetes_groups = ["techchallenge"]
  type              = "STANDARD"
}