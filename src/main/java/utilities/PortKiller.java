package utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static common.AppiumUtils.*;
import static setup.AndroidBase.ip;
import static setup.AndroidBase.port;

public class PortKiller {

    /**
     * <b>Starts the Appium server after ensuring the specified port is free.</b>
     * <p>This method attempts to find and kill any process using the specified port. If successful, it starts the Appium server.</p>
     */
    public static void startServer() {
        logInfo("---------------- Port Killer ----------------");
        try {
            String pid = findProcessIdByPort(port);
            if (pid != null) {
                logWarning("Process ID " + pid + " found on port " + port);
                killProcessByPid(pid);
                // Verify if the process is killed
                if (isProcessRunning(port)) {
                    logError("Failed to kill the process on port " + port);
                } else {
                    logInfo("Port is free now. Process ID " + pid + " is no longer using port " + port);
                    service = startAppiumService(ip, port);
                }
            } else {
                logInfo("Port is free. No process is using port " + port);
                service = startAppiumService(ip, port);
            }
        } catch (Exception e) {
            logError("[" + e.getClass().getSimpleName() + "] | Message --> " + e.getMessage());
        }
    }

    /**
     * <b>Finds the process ID (PID) using the specified port.</b>
     * <p>This method executes a system command to find the PID of the process that is using the given port.</p>
     *
     * @param port the port number to check.
     * @return the process ID using the specified port, or null if no process is found.
     * @throws IOException if an I/O error occurs during the command execution.
     */
    private static String findProcessIdByPort(int port) throws IOException {
        String command = "netstat -ano | findstr :" + port; // command to check what process is running in given port number
        Process process = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", command});

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        String pid = null;

        while ((line = reader.readLine()) != null) {
            logInfo("netstat output: " + line);
            String[] tokens = line.trim().split("\\s+");
            if (tokens.length > 4 && !tokens[3].equals("TIME_WAIT")) {
                pid = tokens[4];
                break;
            }
        }

        reader.close();

        return pid;
    }

    /**
     * <b>Kills the process with the specified PID.</b>
     * <p>This method executes a system command to forcefully terminate the process with the given PID.</p>
     *
     * @param pid the process ID to kill.
     * @throws IOException if an I/O error occurs during the command execution.
     */
    private static void killProcessByPid(String pid) throws IOException {
        logInfo("Attempting to kill process " + pid);
        String command = "taskkill /F /PID " + pid; // command to kill process with the given process ID
        Process process = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", command});

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;

        while ((line = reader.readLine()) != null) {
            logInfo("taskkill output: " + line);
        }

        reader.close();
    }

    /**
     * <b>Checks if any process is currently running on the specified port.</b>
     * <p>This method executes a system command to verify if any process is listening on the given port.</p>
     *
     * @param port the port number to check.
     * @return true if a process is running on the specified port, false otherwise.
     * @throws IOException if an I/O error occurs during the command execution.
     */
    private static boolean isProcessRunning(int port) throws IOException {
        String command = "netstat -ano | findstr :" + port;  // command to check what process is running in given port number
        Process process = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", command});

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        boolean isRunning = false;

        while ((line = reader.readLine()) != null) {
            logInfo("Verification netstat output: " + line);
            if (line.contains("LISTENING")) {
                isRunning = true;
                break;
            }
        }

        reader.close();

        return isRunning;
    }
}
