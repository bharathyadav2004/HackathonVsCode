# Shamir's Secret Sharing Algorithm

This project implements a simplified version of Shamir's Secret Sharing algorithm, which allows for the secure sharing of a secret (in this case, the constant term of a polynomial) among multiple parties. The project includes the ability to read test cases from JSON files, decode values in different bases, and find the secret constant term of the polynomial, along with any wrong points (imposter points) that do not lie on the curve.

## Features

- **Read Test Cases:** Load test case data from JSON files.
- **Decode Y Values:** Decode Y values encoded in different numerical bases.
- **Find Secret:** Calculate the constant term \( c \) of the polynomial based on the decoded values.
- **Identify Wrong Points:** Determine any points that do not lie on the polynomial curve in the second test case.

## Requirements

- Java 8 or higher
- JSON library (`json-20210307.jar`) for handling JSON data

## Getting Started

### Installation

1. Clone this repository:
   ```bash
   git clone <repository-url>
   cd <repository-directory>
