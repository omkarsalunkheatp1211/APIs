# Java API Integration Toolkit

A Java-based toolkit for integrating and utilizing various APIs for data retrieval and analysis, including phone number validation and social media search.

## Table of Contents
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Architecture](#architecture)
- [Setup](#setup)

## Features
- Phone number validation and information lookup using Numverify API
- Landline number verification and carrier detection using Numverify API
- Social media profile search using SerpApi
- Command-line interface for easy interaction
- Automatic region code addition for phone numbers
- Detailed result presentation

## Technologies Used
- Java
- JSON processing with org.json library
- HttpURLConnection for API requests
- Gson for JSON handling (for future implementation)
- JSoup for HTML parsing (for future implementation)

## Architecture
The project follows a simple modular architecture with separate classes for different API integrations:

- **Main.java**: Central command-line interface that provides menu-driven access to all features
- **NumverifyLookup.java**: Standalone implementation for phone number validation
- **SerpApiExample.java**: Implementation for social media profile searches

## Setup

### Requirements
- Java JDK 8 or higher
- IntelliJ IDEA (recommended)
- Internet connection for API access

### Installation Steps

1. Clone the repository:
```bash
git clone https://github.com/omkarsalunkheatp1211/APIs.git
cd APIs
```

2. Set up API keys:
   - Sign up for API keys at:
     - [Numverify](https://numverify.com/)
     - [SerpApi](https://serpapi.com/)
   - Replace the placeholder API keys in the code files:
     - `NUMVERIFY_API_KEY` in Main.java and NumverifyLookup.java
     - `SERP_API_KEY` in Main.java and SerpApiExample.java

4. Add dependencies:
   - The required JAR files are already included in the project:
     - json-20210307.jar
     - jsoup-1.19.1.jar
     - gson-2.10.1-javadoc.jar
   - Make sure they are properly configured in the project structure

