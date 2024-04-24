curl -X PUT "http://localhost:9200/article" -H 'Content-Type: application/json' -d'
{
  "mappings": {
    "properties": {
      "article_id": {
        "type": "keyword"
      },
      "website": {
        "type": "keyword"
      },
      "category": {
        "type": "keyword"
      },
      "title": {
        "type": "text",
        "analyzer": "ik_max_word",
        "search_analyzer": "ik_smart"
      },
      "url": {
        "type": "keyword"
      },
      "introduction": {
        "type": "text",
        "analyzer": "ik_max_word",
        "search_analyzer": "ik_smart"
      },
      "content": {
        "type": "text",
        "analyzer": "ik_max_word",
        "search_analyzer": "ik_smart"
      },
      "articleDate": {
        "type":   "date",
        "format": "strict_date_time"
      },
      "createdDate": {
        "type":   "date",
        "format": "strict_date_time"
      },
      "updatedDate": {
        "type":   "date",
        "format": "strict_date_time"
      }
    }
  }
}'

curl -X DELETE "http://localhost:9200/article"