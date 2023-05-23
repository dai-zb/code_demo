import subprocess

import cv2


def convert_h265_to_h264(input_file, output_file):
    # 打开H.265视频文件
    video_capture = cv2.VideoCapture(input_file)

    # 获取原始视频的宽度、高度和帧率
    width = int(video_capture.get(cv2.CAP_PROP_FRAME_WIDTH))
    height = int(video_capture.get(cv2.CAP_PROP_FRAME_HEIGHT))
    fps = video_capture.get(cv2.CAP_PROP_FPS)

    # 创建输出视频编码器
    fourcc = cv2.VideoWriter_fourcc(*'h264')
    # fourcc = cv2.VideoWriter_fourcc(*'mp4v')
    video_writer = cv2.VideoWriter(output_file, fourcc, fps, (width, height))

    while True:
        # 读取一帧
        ret, frame = video_capture.read()

        if not ret:
            break

        # 写入转码后的H.264帧到输出视频文件
        video_writer.write(frame)

        # cv2.imshow("capture", frame)  # 显示
        # if cv2.waitKey(1) & 0xff == ord('q'):  # 按q退出
        #     break

    # 释放资源
    video_capture.release()
    # video_writer.release()


def convert_h265_to_h264(input_file, output_file):
    # 使用FFmpeg进行转码
    subprocess.call(['ffmpeg', '-i', input_file, '-c:v', 'libx264', output_file])
    """
    -i：指定输入文件的路径。
    -c:v：指定视频编码器。在此示例中，我们使用libx264作为H.264编码器。

    subprocess.call()是Python标准库中的一个函数，用于执行外部命令。
    它会在调用外部命令时阻塞当前线程，直到外部命令执行完成。如果需要保持应用程序的响应性，可以考虑使用subprocess.Popen()或异步操作来执行外部命令。
    """


if __name__ == '__main__':
    convert_h265_to_h264("stream_0.h265", 'output.h264.mp4')
