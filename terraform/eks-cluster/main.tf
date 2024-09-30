provider "aws" {
  region = "us-east-1"
}

module "eks" {
  source          = "terraform-aws-modules/eks/aws"
  cluster_name    = "java-spring-app-cluster"
  cluster_version = "1.22"
  subnets         = ["subnet-xxxxxxxx", "subnet-xxxxxxxx"]
  vpc_id          = "vpc-xxxxxxxx"
  node_groups = {
    eks_nodes = {
      desired_capacity = 2
      max_capacity     = 5
      min_capacity     = 1
      instance_type    = "t3.medium"
    }
  }
}