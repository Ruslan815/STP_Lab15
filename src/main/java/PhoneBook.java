public class PhoneBook {

    public static FramePhoneBook mainFrame;
    public static RecordService recordService;
    public static FileService fileService;

    public PhoneBook() {
        recordService = new RecordService();
        fileService = new FileService();
        mainFrame = new FramePhoneBook();
    }
}
