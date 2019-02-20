import { Moment } from 'moment';
import { IPersonel } from 'app/shared/model/personel.model';
import { IUnvan } from 'app/shared/model/unvan.model';
import { IEkip } from 'app/shared/model/ekip.model';

export const enum Cinsiyet {
    ERKEK = 'ERKEK',
    KADIN = 'KADIN'
}

export const enum EgitimDurumu {
    ILKOKUL = 'ILKOKUL',
    ORTAOKUL = 'ORTAOKUL',
    LISE = 'LISE',
    ONLISANS = 'ONLISANS',
    LISANS = 'LISANS',
    YUKSEK_LISANS = 'YUKSEK_LISANS'
}

export const enum KanGrubu {
    A_RH_POZITIF = 'A_RH_POZITIF',
    A_RH_NEGATIF = 'A_RH_NEGATIF',
    B_RH_NEGATIF = 'B_RH_NEGATIF',
    B_RH_POZITIF = 'B_RH_POZITIF',
    O_RH_POZITIF = 'O_RH_POZITIF',
    O_RH_NEGATIF = 'O_RH_NEGATIF',
    AB_RH_POZITIF = 'AB_RH_POZITIF',
    AB_RH_NEGATIF = 'AB_RH_NEGATIF'
}

export const enum PersonelTuru {
    MAASLI = 'MAASLI',
    GOTURU = 'GOTURU',
    YEVMIYE = 'YEVMIYE'
}

export const enum MedeniHali {
    BEKAR = 'BEKAR',
    EVLI = 'EVLI'
}

export interface IPersonel {
    id?: number;
    tckimlikno?: number;
    ad?: string;
    cepTelefon?: string;
    sabitTelefon?: string;
    eposta?: string;
    cinsiyet?: Cinsiyet;
    egitimDurumu?: EgitimDurumu;
    kanGrubu?: KanGrubu;
    personelTuru?: PersonelTuru;
    ucret?: number;
    iban?: string;
    medeniHali?: MedeniHali;
    dogumTarihi?: Moment;
    girisTarihi?: Moment;
    izinHakedis?: number;
    cikisTarihi?: Moment;
    resimContentType?: string;
    resim?: any;
    dosyaContentType?: string;
    dosya?: any;
    not?: any;
    yonetici?: IPersonel;
    unvan?: IUnvan;
    ekips?: IEkip[];
}

export class Personel implements IPersonel {
    constructor(
        public id?: number,
        public tckimlikno?: number,
        public ad?: string,
        public cepTelefon?: string,
        public sabitTelefon?: string,
        public eposta?: string,
        public cinsiyet?: Cinsiyet,
        public egitimDurumu?: EgitimDurumu,
        public kanGrubu?: KanGrubu,
        public personelTuru?: PersonelTuru,
        public ucret?: number,
        public iban?: string,
        public medeniHali?: MedeniHali,
        public dogumTarihi?: Moment,
        public girisTarihi?: Moment,
        public izinHakedis?: number,
        public cikisTarihi?: Moment,
        public resimContentType?: string,
        public resim?: any,
        public dosyaContentType?: string,
        public dosya?: any,
        public not?: any,
        public yonetici?: IPersonel,
        public unvan?: IUnvan,
        public ekips?: IEkip[]
    ) {}
}
