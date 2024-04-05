package org.example.contest3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

public class J3 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] split = br.readLine().split(" ");
        int numberDevices = Integer.parseInt(split[0]);
        int numberParts = Integer.parseInt(split[1]);
        int updates = numberParts * (numberDevices - 1);
        int count = 0;
        int timeslots = 0;
        Map<Integer, Device> devices = createDevices(numberDevices, numberParts);
        Map<Integer, Set<Device>> parts = initPartsMap(numberParts, devices);
        Map<Device, Device> p2p = new LinkedHashMap<>();

        while (updates > count) {
            timeslots++;

            // request
            for (int i = 1; i < numberDevices; i++) {
                Device device = devices.get(i);
                if (device.getParts().size() < numberParts) {
                    int part = findRarePart(parts, device);
                    device.setPartNeed(part);
                    Set<Device> partOwners = parts.get(part);
                    Device selected = deviceSelection(partOwners);
                    devices.get(selected.number).getRequestsFrom().add(device);
                }
            }
            // response
            for (int i = 0; i < numberDevices; i++) {
                Device device = devices.get(i);
                Device updateDevice;
                Set<Device> requests = device.getRequestsFrom();
                if (!requests.isEmpty()) {
                    Map<Device, Integer> devicesValue = device.getDevicesValue();

                    if (devicesValue.isEmpty()) {
                        updateDevice = deviceSelectionForResponse(requests);
                    } else {
                        int max = Integer.MIN_VALUE;
                        List<Map.Entry<Device, Integer>> list = devicesValue.entrySet().stream().filter(x -> requests.contains(x.getKey())).toList();

                        if (list.isEmpty()) {
                            updateDevice = deviceSelectionForResponse(requests);
                        } else {
                            for (Map.Entry<Device, Integer> entry : list) {
                                max = Math.max(max, entry.getValue());
                            }

                            if (max > 0) {
                                int finalMax = max;
                                list = list.stream().filter(x -> x.getValue() == finalMax).toList();
                            }

                            if (list.size() > 1) {
                                int min = Integer.MAX_VALUE;
                                for (Map.Entry<Device, Integer> entry : list) {
                                    min = Math.min(min, entry.getKey().getParts().size());
                                }
                                int finalMin = min;
                                updateDevice = list.stream().filter(x -> x.getKey().getParts().size() == finalMin).findFirst().get().getKey();
                            } else {
                                updateDevice = list.getFirst().getKey();
                            }
                        }
                    }
                    if (updateDevice != null) {
                        p2p.put(device, updateDevice);
                        updateDevice.getParts().add(updateDevice.getPartNeed());
                    }
                }
            }

            for (Map.Entry<Device, Device> entry : p2p.entrySet()) {
                Device from = entry.getKey();
                Device to = entry.getValue();
                to.getDevicesValue().put(from, to.getDevicesValue().getOrDefault(from, 0) + 1);
                parts.get(to.getPartNeed()).add(to);
                if (to.getParts().size() == numberParts) {
                    to.setTimeslots(timeslots);
                }
                from.getRequestsFrom().clear();
            }

            count += p2p.size();
            p2p.clear();
        }

        System.out.print(devices.values().stream()
                .map(Device::getTimeslots)
                .skip(1)
                .map(String::valueOf)
                .collect(Collectors.joining(" ")));

    }

    public static Map<Integer, Device> createDevices(int numberDevices, int numberParts) {
        Map<Integer, Device> devices = new LinkedHashMap<>();
        Device device = new Device(0, numberParts);
        Set<Integer> deviceParts = device.getParts();
        for (int i = 0; i < numberParts; i++) {
            deviceParts.add(i);
        }
        devices.put(0, device);
        for (int i = 1; i < numberDevices; i++) {
            devices.put(i, new Device(i, numberParts));
        }
        return devices;
    }

    public static Map<Integer, Set<Device>> initPartsMap(int numberParts, Map<Integer, Device> devicesMap) {
        Map<Integer, Set<Device>> parts = new LinkedHashMap<>();
        for (int i = 0; i < numberParts; i++) {
            Set<Device> devices = new TreeSet<>();
            devices.add(devicesMap.get(0));
            parts.put(i, devices);
        }
        return parts;
    }

    public static Device deviceSelectionForResponse(Set<Device> requests) {
        if (requests.size() > 1) {
            return getDevice(requests);
        } else {
            return requests.stream().findFirst().get();
        }
    }

    public static Device getDevice(Set<Device> requests) {
        int min = Integer.MAX_VALUE;
        for (Device from : requests) {
            min = Math.min(min, from.getParts().size());
        }
        int finalMin = min;
        return requests.stream().filter(x -> x.getParts().size() == finalMin).findFirst().get();
    }

    public static Device deviceSelection(Set<Device> devices) {
        return getDevice(devices);
    }

    public static int findRarePart(Map<Integer, Set<Device>> partsMap, Device device) {
        int min = Integer.MAX_VALUE;
        List<Map.Entry<Integer, Set<Device>>> entryList = partsMap.entrySet().stream().filter(x -> !device.getParts().contains(x.getKey())).toList();
        for (Map.Entry<Integer, Set<Device>> entry : entryList) {
            min = Math.min(min, entry.getValue().size());
        }
        List<Integer> minVal = new ArrayList<>();
        for (Map.Entry<Integer, Set<Device>> entry : entryList) {
            if (min == entry.getValue().size()) {
                minVal.add(entry.getKey());
            }
        }
        return minVal.getFirst();
    }

    public static class Device implements Comparable<Device> {
        private int number, timeslots;
        private Set<Integer> parts;
        private Map<Device, Integer> devicesValue;
        private Set<Device> requestsFrom;
        private int partNeed;

        public Device(int number, int numberParts) {
            this.number = number;
            this.timeslots = 0;
            this.parts = new HashSet<>(numberParts);
            this.requestsFrom = new TreeSet<>();
            this.devicesValue = new TreeMap<>();
        }

        public int getTimeslots() {
            return timeslots;
        }

        public void setTimeslots(int timeslots) {
            this.timeslots = timeslots;
        }

        public Set<Integer> getParts() {
            return parts;
        }

        public Map<Device, Integer> getDevicesValue() {
            return devicesValue;
        }

        public Set<Device> getRequestsFrom() {
            return requestsFrom;
        }

        public int getPartNeed() {
            return partNeed;
        }

        public void setPartNeed(int partNeed) {
            this.partNeed = partNeed;
        }

        @Override
        public int compareTo(Device otherDevice) {
            return Integer.compare(this.number, otherDevice.number);
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            Device device = (Device) object;
            return number == device.number;
        }

        @Override
        public int hashCode() {
            return Objects.hash(number);
        }
    }
}
