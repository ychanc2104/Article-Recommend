curl -XPOST "http://localhost:9200/article/_analyze?pretty" -H 'Content-Type: application/json' -d '{
  "analyzer": "ik_smart",
  "text": "上課時間卻在市場買雞蛋？校長聲明自清：人身攻擊"
}'