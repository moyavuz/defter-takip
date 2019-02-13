import { Moment } from 'moment';
import { IUnvan } from 'app/shared/model/unvan.model';
import { ICalisan } from 'app/shared/model/calisan.model';
import { IEkip } from 'app/shared/model/ekip.model';

export const enum CalisanTuru {
    MAASLI = 'MAASLI',
    GOTURU = 'GOTURU',
    YEVMIYE = 'YEVMIYE',
    TASERE = 'TASERE',
    GECICI = 'GECICI'
}

export interface ICalisan {
    id?: number;
    tckimlikno?: number;
    adi?: string;
    soyadi?: string;
    email?: string;
    phoneNumber?: string;
    girisTarihi?: Moment;
    cikisTarihi?: Moment;
    ucret?: number;
    calisanTuru?: CalisanTuru;
    aktifMi?: boolean;
    unvan?: IUnvan;
    sorumlu?: ICalisan;
    ekips?: IEkip[];
}

export class Calisan implements ICalisan {
    constructor(
        public id?: number,
        public tckimlikno?: number,
        public adi?: string,
        public soyadi?: string,
        public email?: string,
        public phoneNumber?: string,
        public girisTarihi?: Moment,
        public cikisTarihi?: Moment,
        public ucret?: number,
        public calisanTuru?: CalisanTuru,
        public aktifMi?: boolean,
        public unvan?: IUnvan,
        public sorumlu?: ICalisan,
        public ekips?: IEkip[]
    ) {
        this.aktifMi = this.aktifMi || false;
    }
}
