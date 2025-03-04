import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  define: {
    global: "window",
  },
  server: {
    https: {
      cert:
        "-----BEGIN CERTIFICATE-----" +
        "\n" +
        "MIIB6zCCAVSgAwIBAgIJdLmhgOxTfWaEMA0GCSqGSIb3DQEBBQUAMBQxEjAQBgNV" +
        "\n" +
        "BAMTCWxvY2FsaG9zdDAeFw0yNDA5MDMwODAxMDVaFw0yNTA5MDMwODAxMDVaMBQx" +
        "\n" +
        "EjAQBgNVBAMTCWxvY2FsaG9zdDCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA" +
        "\n" +
        "wEAS5zTL55CkEV3eWW+NuubCykFNup7YpeUoiMEbOG6eKoLaW/qqNhgsbICUtKLE" +
        "\n" +
        "hGXnFfbT5CBmfhSriWPUoXcUCPLp+C+W3F7yU3jfZ5fel8/Qc5OFBsQYgk5uSS5I" +
        "\n" +
        "5HGdjRW43tPc3d+iU54HGd9HS+tzDzxqkfWuB6Lg4bMCAwEAAaNFMEMwDAYDVR0T" +
        "\n" +
        "BAUwAwEB/zALBgNVHQ8EBAMCAvQwJgYDVR0RBB8wHYYbaHR0cDovL2V4YW1wbGUu" +
        "\n" +
        "b3JnL3dlYmlkI21lMA0GCSqGSIb3DQEBBQUAA4GBAGhQr/oHzXTz2zsfLCqsnQFL" +
        "\n" +
        "I/cAJ72GuOoefNjBY2NsCIt91p2Ky7WhyJ6MlsyZ6mBLxZZuZUE9mvkJ0/zByOm5" +
        "\n" +
        "0ez1eIh+sqms3QSrLihstg72clhGIOce09eIdZrtI723YpaU+EIGqocGpb6NNqL4" +
        "\n" +
        "zO4bLVLYbx29ncnlqqRz" +
        "\n" +
        "-----END CERTIFICATE-----" +
        "\n",
      key:
        "-----BEGIN RSA PRIVATE KEY-----" +
        "\n" +
        "MIICXgIBAAKBgQDAQBLnNMvnkKQRXd5Zb4265sLKQU26ntil5SiIwRs4bp4qgtpb" +
        "\n" +
        "+qo2GCxsgJS0osSEZecV9tPkIGZ+FKuJY9ShdxQI8un4L5bcXvJTeN9nl96Xz9Bz" +
        "\n" +
        "k4UGxBiCTm5JLkjkcZ2NFbje09zd36JTngcZ30dL63MPPGqR9a4HouDhswIDAQAB" +
        "\n" +
        "AoGBAJjDUUH/Jfd/a5+RI1mV1t0o+KeWdJlSF3zbNvZT27lfzLai+7gS2qXZV5Nw" +
        "\n" +
        "KLnQjK9X+ePuGjkvtcGB0zx84zt3Fg3m5S0OY8leBwQz2x4NqRcXaGLZm1ipd8ZA" +
        "\n" +
        "SHTv4g3xe1DojkocqRfJ0+lLdcRD47BHm3MgrN5BeTvxUjOhAkEA+tB2qUvSh4Wb" +
        "\n" +
        "E+A0Bz6ADvMBqviPITGTLkCyILI1CuqSUDP2iVOuY1RF6lDyBHAawIvCAjo1oGrO" +
        "\n" +
        "Skm27dk8tQJBAMQ5ovAdHXB0ZmO1FWG0UpliBDfXRY4ela2OHZL9HqgQKzf12zHP" +
        "\n" +
        "JKB/NjxTE54J1UmZcPYfaV590CT0XRUnjccCQQCLl5hjUwLiUFbmMgNjAMMPDuqf" +
        "\n" +
        "uGUYyVmsBW/eSLDC2ZkDMFiHR96oA9BBwDlihO4uudoJmVIdbTRRHkPEjwJ9AkEA" +
        "\n" +
        "ro6HQArmcW9a1j/0sWH0SLZFAlfibDcQ97+A142Mg/0+NR3kzD51WsFPwgI1ZVVi" +
        "\n" +
        "A4MYVhpDZeeODhHD7FmvaQJAE0ua0lAHRVr5W7eUYLcO8/FdK0Yhhfbv6T15QuBH" +
        "\n" +
        "3A3vW0ewVzWPjs7dHX0C8iM3CqFkF7DC0vh7qkP9+F7Axg==" +
        "\n" +
        "-----END RSA PRIVATE KEY-----" +
        "\n",
    },
  },
});
