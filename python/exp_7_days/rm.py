import os
import time

def remove_old_files_and_directories():
    # 获取当前目录
    current_directory = os.getcwd()
    
    # 获取当前时间戳
    current_time = time.time()
    
    # 计算 7 天前的时间戳
    threshold_time = current_time - (7 * 24 * 60 * 60)
    
    # 遍历当前目录下的文件和目录
    for entry in os.listdir(current_directory):
        entry_path = os.path.join(current_directory, entry)
        
        # 检查是否为文件
        if os.path.isfile(entry_path):
            file_timestamp = os.path.getmtime(entry_path)
            
            # 检查文件的时间戳是否早于阈值
            if file_timestamp < threshold_time:
                print("Removing file:", entry_path)
                os.remove(entry_path)
        
        # 检查是否为目录
        elif os.path.isdir(entry_path):
            directory_timestamp = os.path.getmtime(entry_path)
            
            # 检查目录的时间戳是否早于阈值
            if directory_timestamp < threshold_time:
                print("Removing directory:", entry_path)
                os.rmdir(entry_path)


if __name__ == "__main__":
    # 执行函数移除 7 天前的文件和目录
    remove_old_files_and_directories()
