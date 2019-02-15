import { Moment } from 'moment';
import { ICalisan } from 'app/shared/model/calisan.model';
import { IUnvan } from 'app/shared/model/unvan.model';
import { IEkip } from 'app/shared/model/ekip.model';

export const enum CalismaTuru {
    MAASLI = 'MAASLI',
    GOTURU = 'GOTURU',
    YEVMIYE = 'YEVMIYE',
    TASERE = 'TASERE',
    GECICI = 'GECICI'
}

export const enum MedeniHali {
    BEKAR = 'BEKAR',
    EVLI = 'EVLI'
}

export interface ICalisan {
    id?: number;
    tckimlikno?: number;
    ad?: string;
    telefon?: string;
    eposta?: string;
    calismaTuru?: CalismaTuru;
    ucret?: number;
    iban?: string;
    medeniHali?: MedeniHali;
    dogumTarihi?: Moment;
    girisTarihi?: Moment;
    cikisTarihi?: Moment;
    yonetici?: ICalisan;
    unvan?: IUnvan;
    ekips?: IEkip[];
}

export class Calisan implements ICalisan {
    constructor(
        public id?: number,
        public tckimlikno?: number,
        public ad?: string,
        public telefon?: string,
        public eposta?: string,
        public calismaTuru?: CalismaTuru,
        public ucret?: number,
        public iban?: string,
        public medeniHali?: MedeniHali,
        public dogumTarihi?: Moment,
        public girisTarihi?: Moment,
        public cikisTarihi?: Moment,
        public yonetici?: ICalisan,
        public unvan?: IUnvan,
        public ekips?: IEkip[]
    ) {}
}
