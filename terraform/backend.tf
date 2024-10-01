terraform {
  backend "s3" {
    bucket = "backend-tf-tech"
    key = "fiap/terraform.tfstate"
    region = "us-east-1"
  }
}