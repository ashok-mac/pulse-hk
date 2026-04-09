# Pulse-HK
AI Enabled HK LifeStyle Campanion Agent

## Setup 

- Replace Alibaba Dashscope API_KEY and URL 
- Prefer Qwen3.5-Plus and Qwen3 Models

```bash
mvn clean install
mvn spring-boot:run

```

## Features 

- For a BrandName , Finds the Offer Available across HK with Product details (Sourced from : HK Online Price Watcher https://online-price-watch.consumer.org.hk/opw/opendata/pricewatch.json) 



## Test 

Refer offertest.http file 

## Samples 

```bash
User: What are the offers in Kowloon Dairy?
Assistant: The offers for Kowloon Dairy are as follows: At WELLCOME, Slimilk - Low Fat Hi-Ca Milk Drink 946mL is available at a price of 2 items for $36.00. PARKNSHOP has several offers including 100% Fresh Milk 236mL where you can buy 2 to save $3.00, Deluxe Chocolate Slimilk Milk Drink 236mL and Hi-Calcium Chocolate Slimilk Milk Drink 236mL both with an offer to buy 2 to save $3.00, Skimmed Hi-Ca Milk Drink 236mL and Slimilk - Low Fat Hi-Ca Milk Drink 236mL both allowing you to buy 2 to save $1.50. JASONS also provides the Slimilk - Low Fat Hi-Ca Milk Drink 946mL at a price of 2 items for $36.00.
```


```json

{
  "jsonrpc": "2.0",
  "id": "req-002",
  "result": {
    "id": "c2a26d46-d254-4d5a-b1ae-1277ba2c8b33",
    "contextId": "786a95f9-b2ee-42f7-8ce0-39d82050f914",
    "status": {
      "state": "completed",
      "timestamp": "2026-04-09T10:21:27.66486Z"
    },
    "artifacts": [
      {
        "artifactId": "62e38822-6eb3-4e8e-ba9f-7ce7a7119746",
        "parts": [
          {
            "text": "The offers for Kowloon Dairy are as follows: At WELLCOME, Slimilk - Low Fat Hi-Ca Milk Drink 946mL is available at a price of 2 items for $36.00. PARKNSHOP has several offers including 100% Fresh Milk 236mL where you can buy 2 to save $3.00, Deluxe Chocolate Slimilk Milk Drink 236mL and Hi-Calcium Chocolate Slimilk Milk Drink 236mL both with an offer to buy 2 to save $3.00, Skimmed Hi-Ca Milk Drink 236mL and Slimilk - Low Fat Hi-Ca Milk Drink 236mL both allowing you to buy 2 to save $1.50. JASONS also provides the Slimilk - Low Fat Hi-Ca Milk Drink 946mL at a price of 2 items for $36.00.",
            "kind": "text"
          }
        ]
      }
    ],
    "history": [
      {
        "role": "user",
        "parts": [
          {
            "text": "What are the offers in Kowloon Dairy",
            "kind": "text"
          }
        ],
        "messageId": "msg-user-002",
        "contextId": "786a95f9-b2ee-42f7-8ce0-39d82050f914",
        "taskId": "c2a26d46-d254-4d5a-b1ae-1277ba2c8b33",
        "kind": "message"
      }
    ],
    "kind": "task"
  }
}

```

