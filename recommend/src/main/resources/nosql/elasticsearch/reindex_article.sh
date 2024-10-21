curl -X PUT "http://localhost:9200/article2" -H 'Content-Type: application/json' -d'
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

curl -X POST "http://localhost:9200/_reindex" -H 'Content-Type: application/json' -d'
{
  "source": {
    "index": "article"
  },
  "dest": {
    "index": "article2"
  }
}'

curl -X DELETE "http://localhost:9200/article"

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

curl -X POST "http://localhost:9200/_reindex" -H 'Content-Type: application/json' -d'
{
  "source": {
    "index": "article2"
  },
  "dest": {
    "index": "article"
  }
}'

curl -X DELETE "http://localhost:9200/article2"
