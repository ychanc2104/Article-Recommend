#!/bin/bash

update_config() {
    local file_path=$1
    local keyword=$2

    # Check if the line exists in the file
    if grep -q "$keyword" $file_path; then
        # If the line exists, replace it
        sed -i "s/$keyword: true/$keyword: false/g" $file_path
    else
        # If the line does not exist, append it
        echo "$keyword: false" >> $file_path
    fi
}

FILE_PATH="./elasticsearch-8.12.2"
# Specify the file path
FILE_PATH_CONFIG="$FILE_PATH/config/elasticsearch.yml"

update_config $FILE_PATH_CONFIG "xpack.security.enabled"
update_config $FILE_PATH_CONFIG "xpack.security.enrollment.enabled"
update_config $FILE_PATH_CONFIG "enabled"

# install ik
echo 'y' | $FILE_PATH/bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v8.12.2/elasticsearch-analysis-ik-8.12.2.zip
wget -O $FILE_PATH/config/analysis-ik/zh_words.txt https://raw.githubusercontent.com/p208p2002/zh-nlp-dict/main/zh_words.txt
sed -i "s/ext_dict/zh_words.txt/g" $FILE_PATH/config/analysis-ik/IKAnalyzer.cfg.xml

touch $FILE_PATH/config/jvm.options.d/jvm.options
echo "-Xms4g" >> $FILE_PATH/config/jvm.options.d/jvm.options
echo "-Xmx4g" >> $FILE_PATH/config/jvm.options.d/jvm.options
